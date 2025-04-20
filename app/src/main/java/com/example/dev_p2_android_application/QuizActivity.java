/*
 *@abstract: Controls the quiz question screen of app
 *           Goes off of play_game.xml to display score, timer, question, and answers
 */

package com.example.dev_p2_android_application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// Called on by PlayGame.java; Button pressed, user should be sent to questions page
public class QuizActivity extends AppCompatActivity {
    private TextView scoreText;
    private TextView timerText;
    private Button optionOne, optionTwo, optionThree, optionFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        // Displays UI
        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);

        TextView questionText = findViewById(R.id.questionText);
        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        optionFour = findViewById(R.id.optionFour);
    }
}
