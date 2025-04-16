package com.example.dev_p2_android_application.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.dev_p2_android_application.database.entities.playerScoreLog;
import com.example.dev_p2_android_application.database.playerScoreDatabase;

import java.util.List;

@Dao
public interface PlayerScoreDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(playerScoreLog PlayerScoreLog);

    @Query("SELECT * FROM " + playerScoreDatabase.PLAYER_SCORE_TABLE)
    List<playerScoreLog> getAllRecords();

}