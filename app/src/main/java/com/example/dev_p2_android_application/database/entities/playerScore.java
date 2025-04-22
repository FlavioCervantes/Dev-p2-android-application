package com.example.dev_p2_android_application.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dev_p2_android_application.database.AppDatabase;

import java.util.Objects;

@Entity(tableName = AppDatabase.PLAYER_SCORE_TABLE)
public class playerScore{

    @PrimaryKey(autoGenerate = true)
    public int gamesPlayed;
    public String loginID;
    public String Wins;
    public String Losses;

    public void PlayerScore(int gamesPlayed, String loginID, String wins, String losses) {
        this.gamesPlayed = gamesPlayed;
        this.loginID = loginID;
        this.Wins = wins;
        this.Losses = losses;
    }
      @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        playerScore that = (playerScore) o;
        return gamesPlayed == that.gamesPlayed && Objects.equals(loginID, that.loginID) && Objects.equals(Wins, that.Wins) && Objects.equals(Losses, that.Losses);
    }


    @Override
    public int hashCode() {
        return Objects.hash(gamesPlayed, loginID, Wins, Losses);
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getWins() {
        return Wins;
    }

    public void setWins(String wins) {
        Wins = wins;
    }

    public String getLosses() {
        return Losses;
    }

    public void setLosses(String losses) {
        Losses = losses;
    }

}