package com.example.dev_p2_android_application.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ActiveDirectory.class}, version = 1, exportSchema = false)
public abstract class TriviaGameDatabase extends RoomDatabase {
    public abstract ActiveDirectoryDAO activeDirectoryDAO();
}
