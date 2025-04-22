/*
 *@abstract: Controls the quiz question screen of app
 *           Goes off of play_game.xml to display score, timer, question, and answers
 */

package com.example.dev_p2_android_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

// Called on by PlayGame.java; Button pressed, user should be sent to questions page
public class QuizActivity extends AppCompatActivity {
    private TextView scoreText;
    private TextView timerText;
    private Button optionOne, optionTwo, optionThree, optionFour;

    // TODO: DETERMINE WHICH ANSWER IS CORRECT!!
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

        // QUIT button in upper right hand corner
        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizActivity.this, PlayGame.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    // Responsible for checking if an answer is correct
    private void checkAnswer(Button selectedButton, String selectedAnswer, String correctAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
        }
        else {
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        }

        // Disable other buttons after user makes a selection
        disableOptionButtons();
    }

    // Makes all other buttons unselectable after user chooses an answer
    private void disableOptionButtons() {
        findViewById(R.id.optionOne).setEnabled(false);
        findViewById(R.id.optionTwo).setEnabled(false);
        findViewById(R.id.optionThree).setEnabled(false);
        findViewById(R.id.optionFour).setEnabled(false);
    }
}
