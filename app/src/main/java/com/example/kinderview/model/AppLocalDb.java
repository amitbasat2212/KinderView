package com.example.kinderview.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Post.class,Profile.class}, version = 60)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDAO PostDao();
    public abstract ProfileDAO profileDAO();
}

public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

