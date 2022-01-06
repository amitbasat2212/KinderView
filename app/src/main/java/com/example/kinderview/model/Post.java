package com.example.kinderview.model;

import java.util.Date;

public class Post {

    String status;
    String username;
    Date date;

    public Post(String status, String username, Date date) {
        this.status = status;
        this.username = username;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Post() {

    }

}
