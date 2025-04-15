package com.example.dev_p2_android_application;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
//test

@Dao
public interface TriviaQuestionsDAO {

    @Query("SELECT * FROM " + TriviaQuestionsDatabase.TRIVIA_QUESTIONS_TABLE)
    List<Questions> getAllQuestions();

@Insert
        void insert(Questions question);
}
