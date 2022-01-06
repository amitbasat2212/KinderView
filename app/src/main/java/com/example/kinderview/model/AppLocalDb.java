package com.example.kinderview.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Profile.class}, version = 1)
    abstract class AppLocalDbRepository extends RoomDatabase {
        public abstract ProfileDAO studentDao();
    }
    public class AppLocalDb{
        static public AppLocalDbRepository db =
                Room.databaseBuilder(MyApplication.getContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                        .fallbackToDestructiveMigration()
                        .build();
    }

