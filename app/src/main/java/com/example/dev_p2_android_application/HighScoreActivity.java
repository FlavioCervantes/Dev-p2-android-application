package com.example.dev_p2_android_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HighScoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        // QUIT button in upper right hand corner
        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(v -> {
            Intent intent = new Intent(HighScoreActivity.this, PlayGame.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
