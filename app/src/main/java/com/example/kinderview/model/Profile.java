package com.example.kinderview.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    boolean coonect;
    String urlImage;
    final public static String COLLECTION_NAME2 = "profiles";

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public boolean isCoonect() {
        return coonect;
    }

    public void setCoonect(boolean coonect) {
        this.coonect = coonect;
    }



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

    public Map<String,Object> tojson(){
        Map<String,Object> json =new HashMap<>();
        json.put("name",name);
        json.put("email",email);
        json.put("address",address);
        json.put("passworsd",password);
        json.put("isEducator",isEducator);
        json.put("isParent",isParent);
        json.put("phone",phone);
        json.put("coonect",coonect);
        json.put("urlImage",urlImage);

        return json;

    }

    public static Profile create(Map<String, Object> json) {
        String name1 = (String) json.get("name");
        String email1 = (String) json.get("email");
        String adress1 = (String) json.get("address");
        String password1 = (String) json.get("passworsd");
        String phone1 = (String) json.get("phone");
        Boolean connect1 = (boolean) json.get("coonect");
        String urlImagep = (String) json.get("urlImage");
        Boolean isEducator1 = (boolean) json.get("isEducator");
        Boolean isParent1 = (boolean) json.get("isParent");
        Timestamp ts = (Timestamp)json.get("updateDate")    ;

        Profile profile=new Profile(name1,adress1,email1,password1,isEducator1,isParent1,phone1);
        profile.setUrlImage(urlImagep);
        profile.setCoonect(connect1);

        return profile;
    }


}
