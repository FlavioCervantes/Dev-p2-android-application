package com.example.dev_p2_android_application.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.dev_p2_android_application.database.entities.ActiveDirectory;

//TODO: Make ActiveDirectory class

@Database(entities = {ActiveDirectory.class}, version = 1, exportSchema = false)
public abstract class TriviaGameDatabase extends RoomDatabase {
    public abstract ActiveDirectoryDAO activeDirectoryDAO();

    private static volatile ActiveDirectoryDatabase INSTANCE;

    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static ActiveDirectoryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ActiveDirectoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ActiveDirectoryDatabase.class,
                            "active_directory_db"
                    )
                            .fallbackToDestructiveMigration()
                            .addCallback(prepopulateAdminCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback prepopulateAdminCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ActiveDirectoryDAO dao = INSTANCE.activeDirectoryDAO();

                ActiveDirectory admin = new ActiveDirectory();
                admin.userName = "admin";
                admin.password = "admin";
                admin.role = "admin";
                admin.fullName = "Admin Admin";

                dao.insertUser(admin);
            });
        }
    };
}