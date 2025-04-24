package com.example.dev_p2_android_application.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dev_p2_android_application.HighScore;

import java.util.List;

@Dao
public interface HighScoreDAO {
    @Insert
    void insert(HighScore highScore);

    @Query("SELECT * FROM high_scores ORDER BY score DESC LIMIT 5")
    List<HighScore> getHighFiveScores();
}
