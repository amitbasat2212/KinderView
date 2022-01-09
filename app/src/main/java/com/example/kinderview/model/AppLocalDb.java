package com.example.kinderview.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kinderview.model.MyApplication;

@Database(entities = {Post.class}, version = 8)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDAO PostDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

