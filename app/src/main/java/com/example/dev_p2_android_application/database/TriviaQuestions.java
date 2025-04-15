package com.example.dev_p2_android_application.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity
public class TriviaQuestions {
    @PrimaryKey(autoGenerate = true)
    public int questionId;
    public String question;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;
    public String correctAnswer;


    public TriviaQuestions(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }
}

 /*
@Entity(tableName = "triviaQuestions")
public class TriviaQuestions {
    @PrimaryKey(autoGenerate = true)
    public int questionId;
    public String type;
    public String optionOne;
    public String optionTwo;
    public String optionThree;
    public String optionFour;
    public String correctAnswer;

   /* public TriviaQuestions(String type, String optionOne, String optionTwo, String optionThree, String optionFour, String correctAnswer) {
        this.type = type;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriviaQuestions that = (TriviaQuestions) o;
        return questionId == that.questionId && Objects.equals(type, that.type) && Objects.equals(optionOne, that.optionOne) && Objects.equals(optionTwo, that.optionTwo) && Objects.equals(optionThree, that.optionThree) && Objects.equals(optionFour, that.optionFour) && Objects.equals(correctAnswer, that.correctAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, type, optionOne, optionTwo, optionThree, optionFour, correctAnswer);
    }
}*/