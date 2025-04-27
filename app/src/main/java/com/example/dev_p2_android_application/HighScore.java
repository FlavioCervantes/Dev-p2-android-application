package com.example.dev_p2_android_application;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "high_scores")
public class HighScore {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public int score;

    public HighScore(String username, int score) {
        this.username = username;
        this.score = score;
    }
}
