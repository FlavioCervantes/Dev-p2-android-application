package com.example.dev_p2_android_application.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dev_p2_android_application.database.playerScoreDatabase;

import java.util.Objects;

@Entity(tableName = playerScoreDatabase.PLAYER_SCORE_TABLE)
public class playerScore{

    @PrimaryKey(autoGenerate = true)
    public int gamesPlayed;
    public String loginID;
    public String wins;
    public String losses;

    public playerScore(int gamesPlayed, String loginID, String wins, String losses) {
        this.gamesPlayed = gamesPlayed;
        this.loginID = loginID;
        this.wins = wins;
        this.losses = losses;
    }

    @NonNull
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        playerScore that = (playerScore) o;
        return gamesPlayed == that.gamesPlayed && Objects.equals(loginID, that.loginID) && Objects.equals(wins, that.wins) && Objects.equals(losses, that.losses);
    }


    @Override
    public int hashCode() {
        return Objects.hash(gamesPlayed, loginID, wins, losses);
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
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getLosses() {
        return losses;
    }

    public void setLosses(String losses) {
        this.losses = losses;
    }

}