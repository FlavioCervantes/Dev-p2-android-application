package com.example.dev_p2_android_application.database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dev_p2_android_application.Questions;
import com.example.dev_p2_android_application.TriviaQuestionsDAO;

import java.util.List;

public class QuestionsRepo {
    private TriviaQuestionsDAO mtriviaQuestionsDAO;
    private LiveData<List<Questions>> mAllQuestions;

    public QuestionsRepo(Application application) {
        TriviaGameDatabase db = TriviaGameDatabase.getInstance(application);
        mtriviaQuestionsDAO = db.triviaQuestionsDAO();
        mAllQuestions = (LiveData<List<Questions>>) mtriviaQuestionsDAO.getAllQuestions();
    }


    public LiveData<List<Questions>> getAllQuestions() {
        return mAllQuestions;
    }

}
