package com.example.dev_p2_android_application;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;



//@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {Questions.class}, version = 1)
public abstract class TriviaGameDatabase extends  RoomDatabase {

    private static final String DATABASE_NAME = "triviaGame_database";
    public static final String TRIVIA_GAME_LOG_TABLE = "triviaGameLog";
    private static TriviaGameDatabase INSTANCE;

    public abstract TriviaQuestionsDAO triviaQuestionsDAO();

    public static synchronized TriviaGameDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TriviaGameDatabase.class,
                            DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDBCallback)
                    .build();
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback RoomDBCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(INSTANCE).execute();
        }
    };


//TODO: Create private static class popdbasync.

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {

        private final TriviaQuestionsDAO triviaWordDAO;

        private PopulateDBAsyncTask(TriviaGameDatabase db) {
            triviaWordDAO = db.triviaQuestionsDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            triviaWordDAO.insert(new Questions("Red is the color of which fruit?", "Apple", "Banana", "Grapes", "Orange", 1));
            triviaWordDAO.insert(new Questions("Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Saturn", 2));
            triviaWordDAO.insert(new Questions("What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome", 3));
            triviaWordDAO.insert(new Questions("What is the largest mammal in the world?", "Elephant", "Blue Whale", "Giraffe", "Hippopotamus", 2));
            return null;
        }
    }
















}
