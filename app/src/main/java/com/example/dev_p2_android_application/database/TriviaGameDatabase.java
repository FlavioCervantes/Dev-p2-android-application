package com.example.dev_p2_android_application.database;

import androidx.room.Database;

@Database(entities = {ActiveDirectory.class}, version = 1)
public abstract class TriviaGameDatabase extends RoomDatabase {
    public abstract ActiveDirectoryDAO activeDirectoryDAO();
}
