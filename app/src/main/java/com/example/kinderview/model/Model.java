package com.example.kinderview.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.storage.FirebaseStorage;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {

    //singleton in order to get a model functions in other classes
    public static final Model instance = new Model();
    Executor executor = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());
    ModelFireBase modelFirebase = new ModelFireBase();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public interface SaveImagelistener{
        void onComplete(String url);
    }
    public void saveImage(Bitmap imagebitmap,String imagename,SaveImagelistener listener) {
        modelFirebase.saveImagePost(imagebitmap,imagename,listener);

    }


    public enum PostsListLoadingState{
        loading,
        loaded
    }

    MutableLiveData<PostsListLoadingState> postsListLoadingState = new MutableLiveData<PostsListLoadingState>();
    public LiveData<PostsListLoadingState> getPostsListLoadingState() {
        return postsListLoadingState;
    }

    private Model(){
        postsListLoadingState.setValue(PostsListLoadingState.loaded);
    }

    MutableLiveData<List<Post>> postsList = new MutableLiveData<List<Post>>();
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
                            }
                            AppLocalDb.db.PostDao().insertAll(post);
                            if (lud < post.getUpdateDate()){
                                lud = post.getUpdateDate();
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
    public interface AddPostListener{
        void onComplete();
    }

    public void addPost(Post post, AddPostListener listener){
        modelFirebase.addPost(post, () -> {
            refreshPostList();
            listener.onComplete();
        });


    }

    public void editPost(Post post, AddPostListener listener)
    {
        modelFirebase.addPost(post, () -> {
            refreshPostList();
            listener.onComplete();
        });

    }

    public void deletePost(Post post, AddPostListener listener)
    {
        modelFirebase.deletePost(post, () -> {
            refreshPostList();
            listener.onComplete();
        });

    }

    public interface GetPostById{
        void onComplete(Post student);
    }


    public Post getPostById(String studentId, GetPostById listener) {
        modelFirebase.getPostById(studentId,listener);
        return null;
    }


}