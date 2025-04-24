package com.example.dev_p2_android_application.database;

import android.app.Application;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dev_p2_android_application.HighScore;
import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.database.entities.TriviaQuestions;
import com.example.dev_p2_android_application.database.entities.playerScore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// Define the DBs name and version
@Database(entities = {ActiveDirectory.class, TriviaQuestions.class, playerScore.class, HighScore.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    public static final String PLAYER_SCORE_TABLE = "player_score_table";
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(Application application) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            application.getApplicationContext(),
                            AppDatabase.class,
                            "app_database"
                    ).fallbackToDestructiveMigration().build();
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

    public abstract HighScoreDAO highScoreDAO();

    // El Singleton instance
    //To ensures only one instance of the database is created throughout the app


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

    //Exeutor for DB operations
    // Executor for database operations
    public static ExecutorService getDatabaseWriteExecutor() {
        return databaseWriteExecutor;
    }


}



