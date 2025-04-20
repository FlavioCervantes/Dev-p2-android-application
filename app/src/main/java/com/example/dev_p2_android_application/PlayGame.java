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

        // TODO: Add listeners for 'high score' or 'quit' button?
    }
}
