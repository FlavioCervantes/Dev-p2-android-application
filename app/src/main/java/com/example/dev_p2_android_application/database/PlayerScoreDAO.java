package com.example.dev_p2_android_application.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dev_p2_android_application.database.entities.playerScore;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PlayerScoreDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(playerScore PlayerScore);

    @Query("SELECT * FROM " + AppDatabase.PLAYER_SCORE_TABLE)
    List<playerScore> getAllRecords();


}