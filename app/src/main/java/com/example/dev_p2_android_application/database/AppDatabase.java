package com.example.dev_p2_android_application.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.database.entities.TriviaQuestions;
import com.example.dev_p2_android_application.database.entities.playerScore;


// Define the DBs name and version
@Database(entities = {ActiveDirectory.class, TriviaQuestions.class, playerScore.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Abstract methods to access the  DAOs which are used for DB Operations
    public abstract ActiveDirectoryDAO userDao();
    public abstract TriviaQuestionsDAO triviaQuestionDao();
        public abstract PlayerScoreDAO playerScoreDao();

    // El Singleton instance
    //To ensures only one instance of the database is created throughout the app
    private static volatile AppDatabase INSTANCE;

    // Method to get the singleton instance of the database
    public static AppDatabase getInstance(Context context) {
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
}



