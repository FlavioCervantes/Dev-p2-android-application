package com.example.dev_p2_android_application.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ActiveDirectory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ActiveDirectoryDAO userDao();
}




