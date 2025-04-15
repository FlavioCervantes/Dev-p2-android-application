package com.example.dev_p2_android_application.database;

import  android.app.Application;

import androidx.lifecycle.LiveData;
import com.example.dev_p2_android_application.Questions;
import com.example.dev_p2_android_application.TriviaQuestionsDAO;

import java.util.List;

public class TriviaQuestionsRepository {

    private TriviaQuestionsDAO triviaQuestionsDAO;
    private LiveData<List<Questions>> allQuestions;

    public TriviaQuestionsRepository(Application application) {
        TriviaQuestionsDatabase db = TriviaQuestionsDatabase.getInstance(application);
        triviaQuestionsDAO = db.triviaQuestionsDAO();
        allQuestions = triviaQuestionsDAO.getAllQuestions();
    }

    public LiveData<List<Questions>> getAllQuestions() {
        return allQuestions;
    }

    public void insert(Questions question) {
        triviaQuestionsDAO.insert(question);
    }

    public void deleteAll() {
        triviaQuestionsDAO.deleteAll();
    }

}
