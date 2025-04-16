package com.example.dev_p2_android_application.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dev_p2_android_application.PlayerScoreDAO;
import com.example.dev_p2_android_application.database.entities.playerScoreLog;
import com.example.dev_p2_android_application.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)

@Database(entities = {playerScoreLog.class}, version = 1, exportSchema = false )
public abstract class playerScoreDatabase extends RoomDatabase{

    public static final String PLAYER_SCORE_TABLE = "playerScoreTable";
    private static final String DATABASE_NAME = "playerScoreLog.database";

    private static volatile playerScoreDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static playerScoreDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (playerScoreDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(), playerScoreDatabase.class,
                                    DATABASE_NAME
                            )
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
            Log.i("DAC_playerScoreLog", "DATABASE CREATED!");

        };
    };

    public abstract PlayerScoreDAO playerScoreDAO();
}