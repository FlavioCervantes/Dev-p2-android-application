package com.example.dev_p2_android_application.database.databaseQuestions;

import com.example.dev_p2_android_application.database.databaseQuestions.entities.TriviaQuestions;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
@Dao
public interface TriviaQuestionsDAO {
    @Insert
    void insert(TriviaQuestions questions);

    @Query("SELECT * FROM triviaQuestions")
    List<TriviaQuestions> getAllQuestions();
}
