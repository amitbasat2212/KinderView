package com.example.kinderview.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class Model {

    ModelFireBase modelFireBase = new ModelFireBase();

    int id,likes,comments,proPic,postPic;
    String Name,Time,Status;

    public Model(int id, int likes, int comments, int proPic, int postPic, String name, String time, String status) {
        this.id = id;
        this.likes = likes;
        this.comments = comments;
        this.proPic = proPic;
        this.postPic = postPic;
        Name = name;
        Time = time;
        Status = status;
    }

    MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    public LiveData<List<Post>> getAllPosts(){
        refreshPostsList();
       return posts;
    }

public void refreshPostsList(){
       modelFireBase.getAllPosts();


    }

    public interface getPostListener{
        void onComplete(List<Post> list);
    }

    public void createPost(Post post){
        modelFireBase.createPosts(post);

    }

    public void createProfile(Profile profile){
        modelFireBase.createProfile(profile);

    }

    public void getProfileByUserName(String username){
        modelFireBase.getProfileByUserName(username);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getProPic() {
        return proPic;
    }

    public void setProPic(int proPic) {
        this.proPic = proPic;
    }

    public int getPostPic() {
        return postPic;
    }

    public void setPostPic(int postPic) {
        this.postPic = postPic;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
