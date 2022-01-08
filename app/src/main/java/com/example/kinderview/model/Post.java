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
         String username;
         String date;
         String likes;
         String comment;

    final public static String COLLECTION_NAME = "Posts";
    Long UpdateDate=new Long(0);

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Post(@NonNull String id, String status, String username, String date, String likes, String comment) {
        this.id = id;
        this.status = status;
        this.username = username;
        this.date = date;
        this.likes = likes;
        this.comment = comment;

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
        json.put("username",username);
        json.put("status",status);
        json.put("date",date);
        json.put("comment",comment);
        json.put("likes",likes);
        json.put("updateDate", FieldValue.serverTimestamp());
        return json;

    }

    public static Post create(Map<String, Object> json) {
        String id = (String) json.get("id");
        String username = (String) json.get("username");
        String status = (String) json.get("status");
        String date = (String) json.get("date");
        String likes = (String) json.get("likes");
        String comment = (String) json.get("comment");

        Timestamp ts = (Timestamp)json.get("updateDate");
        Long updateDate = ts.getSeconds();

        Post post = new Post(id,status,username,date,likes,comment);
        post.setUpdateDate(updateDate);
        return post;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Post() {

    }



}
