package com.example.kinderview.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Profile {

    @PrimaryKey
    @NonNull
    String username;
    String name;
    String lastname;
    String address;
    String password;
    Boolean isEducator;
    Boolean isParent;
    int phone;

    public Profile() {

    }


    public Profile(String name, String lastname, String address, String username, String password, Boolean isEducator, Boolean isParent, int phone) {
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.username = username;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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
