/*
 * @abstract: Displays user interface using user_ui.xml and responds to buttons pressed
 * Responsible for Play Game, High Scores, and Quit Game
 */

package com.example.dev_p2_android_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PlayGame extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_ui);

        Button playGameButton = findViewById(R.id.playGameButtonUser);
        playGameButton.setOnClickListener(v -> {
            // TODO: Verify it is QuizActivity that will display questions
            Intent intent = new Intent(PlayGame.this, QuizActivity.class);
            startActivity(intent);
        });

        // TODO: Add listeners for 'high score'
        Button highScoreButton = findViewById(R.id.highScoreButtonUser);
        highScoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlayGame.this, HighScoreActivity.class);
            startActivity(intent);
        });

        //TODO: Add listeners for 'log out'
        Button logOutButton = findViewById(R.id.logOutButtonUser);
        logOutButton.setOnClickListener(v -> {
            //TODO: Clear user activity here, when we get that done
            // TODO: Figure out how to return to login page after this button is pressed
            Intent intent = new Intent(PlayGame.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
