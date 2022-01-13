package com.example.kinderview.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Profile {

    @PrimaryKey
    @NonNull
    String email;
    String name;
    String address;
    String password;
    Boolean isEducator=false;
    Boolean isParent=false;
    String phone;

    public Profile() {

    }


    public Profile(String name, String address, String email, String password, Boolean isEducator, Boolean isParent, String phone) {
        this.name = name;

        this.address = address;
        this.email = email;
        this.password = password;
        this.isEducator = isEducator;
        this.isParent = isParent;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getEducator() {
        return isEducator;
    }

    public void setEducator(Boolean educator) {
        isEducator = educator;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

}
