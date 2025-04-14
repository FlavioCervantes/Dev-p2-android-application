package com.example.dev_p2_android_application.databaseQuestions.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "triviaQuestions")
public class TriviaQuestions {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public String ID = "";
    public String optionOne = "";
    public String optionTwo = "";
    public String optionThree = "";
    public String optionFour = "";

    public TriviaQuestions(String optionOne, String optionTwo, String optionThree, String optionFour) {
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
    }

    @NonNull
    public String getID() {
        return ID;
    }

    public void setID(@NonNull String ID) {
        this.ID = ID;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public String getOptionThree() {
        return optionThree;
    }

    public void setOptionThree(String optionThree) {
        this.optionThree = optionThree;
    }

    public String getOptionFour() {
        return optionFour;
    }

    public void setOptionFour(String optionFour) {
        this.optionFour = optionFour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriviaQuestions that = (TriviaQuestions) o;
        return Objects.equals(ID, that.ID) && Objects.equals(optionOne, that.optionOne) && Objects.equals(optionTwo, that.optionTwo) && Objects.equals(optionThree, that.optionThree) && Objects.equals(optionFour, that.optionFour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, optionOne, optionTwo, optionThree, optionFour);
    }
}
