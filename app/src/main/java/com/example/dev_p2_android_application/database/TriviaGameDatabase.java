package com.example.dev_p2_android_application.database;


import android.arch.persistence.db.SupportSQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.example.dev_p2_android_application.database.entities.TriviaGameLog;

import javax.security.auth.callback.Callback;


//@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {Questions.class}, version = 1)
public abstract class TriviaGameDatabase extends  RoomDatabase{

    private static final String DATABASE_NAME = "triviaGame_database";
    public static final String TRIVIA_GAME_LOG_TABLE = "triviaGameLogTable";

    private static TriviaGameDatabase INSTANCE;

    public abstract TriviaQuestionsDAO triviaQuestionsDAO();

    public static synchronized TriviaGameDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    TriviaGameDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomDBCallback)
                    .build();
        }
        return INSTANCE;
    }


    private static Callback RoomDBCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDBAsyncTask(INSTANCE).execute();

    }
};


//TODO: Create private static class popdbasync.
















}
