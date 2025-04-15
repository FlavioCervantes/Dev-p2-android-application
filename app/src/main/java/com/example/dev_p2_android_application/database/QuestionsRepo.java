package com.example.dev_p2_android_application.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dev_p2_android_application.Questions;
import com.example.dev_p2_android_application.TriviaQuestionsDAO;

import java.util.List;

public class QuestionsRepo {
    private TriviaQuestionsDAO mtriviaQuestionsDAO;
    private LiveData<List<Questions>> mAllQuestions;

    public QuestionsRepo(Application application) {
        TriviaQuestionsDatabase db = TriviaQuestionsDatabase.getInstance(application);
        TriviaQuestionsDAO = db.triviaQuestionsDAO();
        mAllQuestions = (LiveData<List<Questions>>) mtriviaQuestionsDAO.getAllQuestions();
    }


    public LiveData<List<Questions>> getAllQuestions() {
        return mAllQuestions;
    }

}
