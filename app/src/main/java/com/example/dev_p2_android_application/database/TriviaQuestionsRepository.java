package com.example.dev_p2_android_application.database;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dev_p2_android_application.database.entities.TriviaQuestions;

import java.util.List;

public class TriviaQuestionsRepository {
    private final TriviaQuestionsDAO dao;

    public TriviaQuestionsRepository(Application application){
        TriviaQuestionsDatabase db = TriviaQuestionsDatabase.getDatabase(application);
        this.dao = db.triviaQuestionDao();
    }

    public List<TriviaQuestions> getAllQuestions(){
        return dao.getAllQuestions();
    }

    public void insert(TriviaQuestions question){
        new InsertAsyncTask(dao).execute(question);
    }

    private static class InsertAsyncTask extends AsyncTask<TriviaQuestions, Void, Void> {
        private final TriviaQuestionsDAO dao;

        InsertAsyncTask(TriviaQuestionsDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TriviaQuestions... questions){
            dao.insert(questions[0]);
            return null;
        }
    }
}