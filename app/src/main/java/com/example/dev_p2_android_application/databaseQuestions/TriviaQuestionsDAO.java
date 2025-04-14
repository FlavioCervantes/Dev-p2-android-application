package com.example.dev_p2_android_application.databaseQuestions;

import com.example.dev_p2_android_application.databaseQuestions.entities.TriviaQuestions;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
@Dao
public interface TriviaQuestionsDAO {
    @Insert
    void insert(TriviaQuestions questions);

    @Query("Select * from " + TriviaQuestionsDatabase.TRIVIA_QUESTIONS_TABLE)
    List<TriviaQuestions> getAllQuestions();
}
