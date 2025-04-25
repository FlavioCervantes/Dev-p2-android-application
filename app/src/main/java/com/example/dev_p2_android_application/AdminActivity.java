package com.example.dev_p2_android_application;

import static com.example.dev_p2_android_application.R.id.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.dev_p2_android_application.databinding.ActivityMainBinding;
import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.PlayGame;
import com.example.dev_p2_android_application.QuizActivity;

import org.jetbrains.annotations.Contract;

public class AdminActivity extends AppCompatActivity{



    public String playGame;
    public String checkScore;

    ActiveDirectory obj = new ActiveDirectory();
    String roles = obj.role;


    public ActiveDirectory repository;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_ui);


        if (!isAdminAuthenticated()) {
            Toast.makeText(this, "Unauthorized access!", Toast.LENGTH_LONG).show();
            finish();
        }

        playGame = String.valueOf(findViewById(R.id.playGameButton));
        Button playButton = findViewById(R.id.playGameButton);

        checkScore = String.valueOf(findViewById(R.id.highScoreButton));
        Button checkScoreButton = findViewById(R.id.highScoreButton);


        Button questionInput = findViewById(R.id.editQuestionsButton);

        Button quitGame = findViewById(R.id.quitButton);


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(AdminActivity.this, PlayGame.class);
                startActivity(intent);
            }
        });


        checkScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(AdminActivity.this, HighScoreActivity.class);
                startActivity(intent);
            }
        });

        questionInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminActivity.this, EditQuestions.class);
                startActivity(intent);
            }
        });


        quitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                 startActivity(intent);

            }
        });




    }


    private boolean isAdminAuthenticated() {
        return "admin".equals(roles);
    }
}




