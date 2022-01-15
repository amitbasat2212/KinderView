package com.example.kinderview.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Entity
public class Post {

    @PrimaryKey
    @NonNull
    String id="";
    String status;
    String email;
    String date;
    String likes;
    String UrlImagePost;
    boolean delete=false;


    final public static String COLLECTION_NAME = "Posts";
    Long UpdateDate=new Long(0);

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public Post(@NonNull String id, String status, String email, String date, String likes) {
        this.id = id;
        this.status = status;
        this.email = email;
        this.date = date;
        this.likes = likes;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public void setUpdateDate(Long updateDate){
        this.UpdateDate=updateDate;
    }

    public Long getUpdateDate() {
        return UpdateDate;
    }

    public Map<String,Object> tojson(){
        Map<String,Object> json =new HashMap<>();
        json.put("id",id);
        json.put("email",email);
        json.put("status",status);
        json.put("date",date);
        json.put("UrlImagePost",UrlImagePost);
        json.put("likes",likes);
        json.put("delete",delete);
        json.put("updateDate", FieldValue.serverTimestamp());

        return json;

    }

    public static Post create(Map<String, Object> json) {
        String id = (String) json.get("id");
        String username = (String) json.get("email");
        String status = (String) json.get("status");
        String date = (String) json.get("date");
        String likes = (String) json.get("likes");
        String urlImage = (String) json.get("UrlImagePost");
        Boolean delete = (boolean) json.get("delete");
        Timestamp ts = (Timestamp)json.get("updateDate")    ;
        Long updateDate = ts.getSeconds();

        Post post = new Post(id,status,username,date,likes);
        post.setDelete(delete);
        post.setUpdateDate(updateDate);
        post.setUrlImagePost(urlImage);
        return post;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrlImagePost() {
        return UrlImagePost;
    }

    public void setUrlImagePost(String urlImagePost) {
        UrlImagePost = urlImagePost;
    }

    public Post() {

    }


    public void setImagePostUrl(String url) {
        this.UrlImagePost=url;

    }
}