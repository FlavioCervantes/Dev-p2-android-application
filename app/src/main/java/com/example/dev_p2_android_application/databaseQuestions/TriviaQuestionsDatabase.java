package com.example.dev_p2_android_application.databaseQuestions;

import android.content.Context;

import com.example.dev_p2_android_application.databaseQuestions.entities.TriviaQuestions;

@Database(entities = {TriviaQuestions.class}, version = 1, exportSchema = false)
public abstract class TriviaQuestionsDatabase extends RoomDatabase {
    private static volatile TriviaQuestionsDatabase INSTANCE;
    public abstract TriviaQuestionsDAO questionDao();    }

    static TriviaQuestionsDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized(TriviaQuestionsDatabase.class){
                if(INSTANCE == null){
                    INSTANCE =
                    TriviaQuestionsDatabase.class, "trivia_question_database")
                    .fallbackToDestructiveMigration()
                    .addCallbuild(addDefaultValues)
                    .build();
        }
    }
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
       @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);}
    };
}