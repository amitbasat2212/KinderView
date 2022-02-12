package com.example.kinderview.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
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
    String UrlImagePost;
    String ProfilePic;
    boolean delete=false;
    final public static String COLLECTION_NAME = "Posts";
    Long UpdateDate=new Long(0);

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Post(@NonNull String id, String status, String email, String date) {
        this.id = id;
        this.status = status;
        this.email = email;
        this.date = date;
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
        json.put("delete",delete);
        json.put("profil",ProfilePic);
        json.put("updateDate", FieldValue.serverTimestamp());

        return json;

    }

    public static Post create(Map<String, Object> json) {
        String id = (String) json.get("id");
        String username = (String) json.get("email");
        String status = (String) json.get("status");
        String date = (String) json.get("date");
        String urlImage = (String) json.get("UrlImagePost");
        String urlImagepro = (String) json.get("profil");
        Boolean delete = (boolean) json.get("delete");
        Timestamp ts = (Timestamp)json.get("updateDate")    ;
        Long updateDate = ts.getSeconds();

        Post post = new Post(id,status,username,date);
        post.setDelete(delete);
        post.setUpdateDate(updateDate);
        post.setProfilePic(urlImagepro);
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