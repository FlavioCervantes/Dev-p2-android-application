/*
 *@abstract
 */

package com.example.dev_p2_android_application;

public class Questions {
    private String question;
    private String[] options;
    private String correctAnswer;

    public Questions(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}