package com.example.dev_p2_android_application.databaseQuestions;

import com.example.dev_p2_android_application.databaseQuestions.entities.TriviaQuestions;

import java.util.List;

@Dao
public interface TriviaQuestionsDAO {
    @Insert
    void insert(TriviaQuestions questions);

    @Query("Select * from questions")
    List<TriviaQuestions> getAllQuestions();

}
