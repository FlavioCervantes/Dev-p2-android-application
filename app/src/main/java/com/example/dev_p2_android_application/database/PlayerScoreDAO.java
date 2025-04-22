package com.example.dev_p2_android_application.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dev_p2_android_application.database.entities.playerScore;

import java.util.List;

@Dao
public interface PlayerScoreDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(playerScore PlayerScore);

    @Update
    void update(playerScore playerScore);

    @Query("SELECT * FROM " + AppDatabase.PLAYER_SCORE_TABLE)
    List<playerScore> getAllRecords();

    @Query("SELECT * FROM PLAYER_SCORE_TABLE WHERE loginID = :loginID LIMIT 1")
    playerScore getRecordByUserId(int loginID);
}