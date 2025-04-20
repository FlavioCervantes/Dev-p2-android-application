package com.example.dev_p2_android_application.database;

import com.example.dev_p2_android_application.database.entities.TriviaQuestions;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
@Dao
public  interface TriviaQuestionsDAO {
    @Insert
    static void insert(TriviaQuestions triviaQuestions);

    @Query("SELECT * FROM TriviaQuestions")
    List<TriviaQuestions> getAllQuestions();
}
