package com.example.kinderview.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    //singleton in order to get a model functions in other classes
    /**
     * the variables section
     */
    public static final Model instance = new Model();
    public Executor executor = Executors.newFixedThreadPool(2);
    public Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());
    ModelFireBase modelFirebase = new ModelFireBase();
    Profile profile1=new Profile("0","0","0","0",false,false,"0");
    MutableLiveData<PostsListLoadingState> postsListLoadingState = new MutableLiveData<PostsListLoadingState>();
    MutableLiveData<List<Post>> postsList = new MutableLiveData<List<Post>>();


    /**
     * the interface for post and profiles
     */
    public interface SaveImagelistener{
        void onComplete(String url);
    }
    public interface connect{
        void onComplete(Profile profile);
    }
    public interface sighup{
        void onComplete(String email);
    }
    public enum PostsListLoadingState{
        loading,
        loaded
    }

    public interface AddEditDeleteProfileAndPost{
        void onComplete();
    }

    public interface GetPostById{
        void onComplete(Post post);
    }

    public interface GetProfileById{
        void onComplete(Profile profile);
    }


    /**
     * the constructor
     */
    private Model(){
        postsListLoadingState.setValue(PostsListLoadingState.loaded);
    }


    /**
     * the photos -edit and delete and save
     */
    public void deletePic(Post value, String picName, AddEditDeleteProfileAndPost listener) {
        modelFirebase.deleteImagePost(value.UrlImagePost, picName, new AddEditDeleteProfileAndPost() {
            @Override
            public void onComplete() {
                refreshPostList();
                listener.onComplete();
            }
        });
    }

    public void deleteProfilePic(Profile value, String picName, AddEditDeleteProfileAndPost listener) {
        modelFirebase.deleteProfilePic(value.urlImage, picName, new AddEditDeleteProfileAndPost() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public void saveImage(Bitmap imagebitmap,String imagename,SaveImagelistener listener) {
        modelFirebase.saveImagePost(imagebitmap,imagename,listener);

    }


    /**
     * the posts -the home page list ,the edit ,delete and create
     */
    public LiveData<PostsListLoadingState> getPostsListLoadingState() {
        return postsListLoadingState;
    }

    public LiveData<List<Post>> getAll(){
        if (postsList.getValue() == null) { refreshPostList(); };
        return  postsList;
    }
    public void refreshPostList(){
        postsListLoadingState.setValue(PostsListLoadingState.loading);

        // get last local update date
        Long lastUpdateDate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("PostLastUpdateDate",0);

        // firebase get all updates since lastLocalUpdateDate
        modelFirebase.getAllPosts(lastUpdateDate, new ModelFireBase.GetAllPostsListener() {
            @Override
            public void onComplete(List<Post> list) {
                // add all records to the local db
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Long lud = new Long(0);
                        for (Post post: list) {
                            if(post.isDelete()){
                                  AppLocalDb.db.PostDao().delete(post);
                            }else {
                                AppLocalDb.db.PostDao().insertAll(post);
                                if (lud < post.getUpdateDate()){
                                    lud = post.getUpdateDate();
                                }
                            }

                        }
                        // update last local update date
                        MyApplication.getContext()
                                .getSharedPreferences("TAG",Context.MODE_PRIVATE)
                                .edit()
                                .putLong("PostsLastUpdateDate",lud)
                                .commit();

                        //return all data to caller
                        List<Post> stList = AppLocalDb.db.PostDao().getAll();
                        postsList.postValue(stList);
                        postsListLoadingState.postValue(PostsListLoadingState.loaded);
                    }
                });
            }
        });
    }


    public void addPost(Post post, AddEditDeleteProfileAndPost listener){
        modelFirebase.addPost(post, () -> {
            refreshPostList();
            listener.onComplete();
        });


    }

    public void editPost(Post post, AddEditDeleteProfileAndPost listener)
    {
        modelFirebase.addPost(post, () -> {
            refreshPostList();
            listener.onComplete();
        });

    }

    public void deletePost(Post post, AddEditDeleteProfileAndPost listener)
    {

        modelFirebase.deletePost(post, () -> {
            refreshPostList();
            listener.onComplete();
        });

    }

    /**
     * the user Autntication-sighup and login also the edit user
     */
    public Boolean isSignIn (){
        return modelFirebase.isSignIn();
    }

    public void sighup(Profile profile,Model.sighup sighup) {
        modelFirebase.signUp(profile.email, profile.password, new Model.sighup() {
            @Override
            public void onComplete(String email) {
                if(email!=null) {
                    modelFirebase.addProfile(profile, new AddEditDeleteProfileAndPost() {
                        @Override
                        public void onComplete() {
                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    profile1 = profile;
                                    AppLocalDb.db.profileDAO().insertAll(profile);
                                    sighup.onComplete(email);
                                }
                            });
                        }
                    });
                }else{
                    sighup.onComplete(null);
                }
            }
        });
    }


    public void editprofile(Profile profile,AddEditDeleteProfileAndPost addPostListener){
        modelFirebase.addProfile(profile,addPostListener);
    }


    public void sighout(ModelFireBase.sighout sighout) {
        modelFirebase.sighout(new ModelFireBase.sighout() {
            @Override
            public void onComplete() {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        AppLocalDb.db.profileDAO().insertAll(profile1);
                        sighout.onComplete();
                    }
                });
            }
        });


    }

    public void Login (String email,String password, Model.sighup listener){

        modelFirebase.Login(email, password, new  Model.sighup() {
            @Override
            public void onComplete(String email1) {
                if (email1 != null) {
                    getprofilebyEmail(email, new GetProfileById() {
                        @Override
                        public void onComplete(Profile profile) {

                            modelFirebase.connected();
                            listener.onComplete(profile.email);

                        }
                    });

                }else{
                    listener.onComplete(null);
                }
            }
        });
    }

    public void getprofilebyEmail(String email,GetProfileById getProfileById) {
       modelFirebase.getProfileByemail(email, new GetProfileById() {
           @Override
           public void onComplete(Profile profile) {
                getProfileById.onComplete(profile);
           }
       });


    }

    public void getUserConnect(connect connect) {

        if(modelFirebase.isSignIn()) {
            getprofilebyEmail(modelFirebase.currentUser.getEmail(), new GetProfileById() {
                @Override
                public void onComplete(Profile profile) {
                    connect.onComplete(profile);
                }

            });
        }

    }

}