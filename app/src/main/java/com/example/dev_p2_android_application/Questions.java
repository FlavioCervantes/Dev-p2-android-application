package com.example.dev_p2_android_application;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "questions_table")
public class Questions {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "question_id")
    private int questionId;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "answer")
    private String answer;

    @ColumnInfo(name = "option1")
    private String option1;

    @ColumnInfo(name = "option2")
    private String option2;

    @ColumnInfo(name = "option3")
    private String option3;

    @ColumnInfo(name = "option4")
    private String option4;

    public Questions() {
        questionId = 0;
        question = "";
        answer = "";
        option1 = "";
        option2 = "";
        option3 = "";
        option4 = "";


    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public Questions(int questionId, String question, String answer, String option1, String option2, String option3, String option4) {
       this.questionId = questionId;
        this.question = question;
        this.answer = answer;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }



    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
