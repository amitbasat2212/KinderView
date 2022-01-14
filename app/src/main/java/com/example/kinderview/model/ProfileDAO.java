package com.example.kinderview.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao

public interface ProfileDAO {

        @Query("select * from Profile")
        List<Profile> getAll();
        @Query("SELECT * FROM profile WHERE email = :email1")
        public List<Profile> loadprofilewithemail(String email1);
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(Profile... profiles);
        @Delete
        void delete(Profile profile);
}

