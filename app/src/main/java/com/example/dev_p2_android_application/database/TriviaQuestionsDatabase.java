package com.example.dev_p2_android_application.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;

import com.example.dev_p2_android_application.database.entities.TriviaQuestions;
import com.example.dev_p2_android_application.database.typeConverters.LocalDateTypeConverter;

@Database(entities = {TriviaQuestions.class}, version = 1, exportSchema = false)
    private static final String DATABASE_NAME = "trivia_question_database";
    private static volatile TriviaQuestionsDatabase INSTANCE;
    public abstract TriviaQuestionsDAO triviaQuestionDao();

    public static TriviaQuestionsDatabase getDatabase(final Context context){
    if(INSTANCE == null){
        synchronized(TriviaQuestionsDatabase.class){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TriviaQuestionsDatabase.class,
                        DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(addDefaultValues)
                        .build();
            }
        }
    }
    return INSTANCE;
    }


    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBsyncTasks(INSTANCE).execute();
        }
    };

    private static class PopulateDBsyncTasks extends AsyncTask<Void, Void, Void> {
        private final TriviaQuestionsDAO dao;
        PopulateDBsyncTasks(TriviaQuestionsDatabase db){
            dao = db.triviaQuestionDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            return null;
        }
    }
    }