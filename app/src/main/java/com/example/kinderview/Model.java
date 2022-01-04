package com.example.kinderview;

public class Model {

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
