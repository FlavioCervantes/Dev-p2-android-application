/*
 * @abstract: Displays user interface using user_ui.xml and responds to buttons pressed
 * Responsible for Play Game, High Scores, and Quit Game
 */

package com.example.dev_p2_android_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PlayGame extends AppCompatActivity {

    private String role;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        role = intent.getStringExtra("USER_ROLE");
        userId = intent.getIntExtra("USER_ID", -1);

        if("admin".equalsIgnoreCase(role)){
            setContentView(R.layout.admin_ui);
            setupAdminUI();
        } else {
            setContentView(R.layout.user_ui);
            setupUserUI();
        }
    }

    private void setupUserUI() {
        Button playGameButton = findViewById(R.id.playGameButtonUser);
        playGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlayGame.this, QuizActivity.class);
            intent.putExtra("USER_ROLE", role);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        Button highScoreButton = findViewById(R.id.highScoreButtonUser);
        highScoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlayGame.this, HighScoreActivity.class);
            startActivity(intent);
        });

        Button logOutButton = findViewById(R.id.logOutButtonUser);
        logOutButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlayGame.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        ImageView ghostImage = findViewById(R.id.gif);
        Glide.with(this)
                .asGif()
                .load(R.drawable.ghost)
                .into(ghostImage);
    }

    private void setupAdminUI() {
        TextView welcomeText = findViewById(R.id.adminWelcome);
        welcomeText.setText("Welcome, Admin!");

        Button playGameButton = findViewById(R.id.playGameButton);
        playGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlayGame.this, QuizActivity.class);
            intent.putExtra("USER_ROLE", role);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        Button highScoreButton = findViewById(R.id.highScoreButton);
        highScoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlayGame.this, HighScoreActivity.class);
            startActivity(intent);
        });

        Button logOutButton = findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlayGame.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        ImageView ghostImage = findViewById(R.id.gif);
        Glide.with(this)
                .asGif()
                .load(R.drawable.ghost)
                .into(ghostImage);
    }
}