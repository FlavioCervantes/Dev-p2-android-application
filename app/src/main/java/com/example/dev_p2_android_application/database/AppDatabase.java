package com.example.dev_p2_android_application.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dev_p2_android_application.MainActivity;
import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.database.entities.TriviaQuestions;
import com.example.dev_p2_android_application.database.entities.playerScore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// Define the DBs name and version
@Database(entities = {ActiveDirectory.class, TriviaQuestions.class, playerScore.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    public static final String PLAYER_SCORE_TABLE = "player_score_table";
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(final Application application) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            application.getApplicationContext(),
                            AppDatabase.class,
                            "app_database"
                    )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    //DAOs
    // Abstract methods to access the  DAOs which are used for DB Operations
    public abstract ActiveDirectoryDAO activeDirectoryDAO();

    public abstract PlayerScoreDAO playerScoreDAO();

    public abstract TriviaQuestionsDAO triviaQuestionDao();

    // El Singleton instance
    //To ensures only one instance of the database is created throughout the app


    // Method to get the singleton instance of the database
    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "app_database"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    //Exeutor for DB operations
    // Executor for database operations
    public static ExecutorService getDatabaseWriteExecutor() {
        return databaseWriteExecutor;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                ActiveDirectoryDAO userDao = INSTANCE.activeDirectoryDAO();
                userDao.deleteAll();

                ActiveDirectory admin = new ActiveDirectory();
                admin.setUsername("admin1");
                admin.setPassword("admin1");
                admin.setRole("admin");
                admin.setFullName("Admin user");
                userDao.insertUser(admin);

                ActiveDirectory user = new ActiveDirectory();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole("user");
                user.setFullName("User");
                userDao.insertUser(user);


            });
        }
    };
}



