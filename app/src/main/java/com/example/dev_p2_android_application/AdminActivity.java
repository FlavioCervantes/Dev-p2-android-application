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


    private EditText questionInput, optionA, optionB, optionC, optionD, correctAnswer;
    private Button publishBtn;
    private TextView statusView;
    private ActivityMainBinding binding;
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

        // Auth check (simple example)
        if (!isAdminAuthenticated()) {
            Toast.makeText(this, "Unauthorized access!", Toast.LENGTH_LONG).show();
            finish();
        }

        playGame = String.valueOf(findViewById(R.id.playGameButton));


        Button playButton = findViewById(R.id.playGameButton);

        
        checkScore = String.valueOf(findViewById(R.id.highScoreButton));

        Button checkScoreButton = findViewById(R.id.highScoreButton);

        Button quitGame = findViewById(R.id.quitButton);


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                
            }
        });


        checkScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        quitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        /**
        questionInput = findViewById(R.id.editQuestionButton);

        

        questionInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionEdits();
            }
        });

        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishQuestion();
            }
        });
         */
    }


    private boolean isAdminAuthenticated() {

        if (roles == "admin"){

            return true;
        }
        else {

            return false;
        }


    }


    /**
    private void questionEdits() {

        optionA = findViewById(R.id.editTextOptionA);
        optionB = findViewById(R.id.editTextOptionB);
        optionC = findViewById(R.id.editTextOptionC);
        optionD = findViewById(R.id.editTextOptionD);
        correctAnswer = findViewById(R.id.editTextCorrectAnswer);
        publishBtn = findViewById(R.id.buttonPublish);
        statusView = findViewById(R.id.statusText);

    }

    private void publishQuestion() {
        String question = questionInput.getText().toString().trim();
        String a = optionA.getText().toString().trim();
        String b = optionB.getText().toString().trim();
        String c = optionC.getText().toString().trim();
        String d = optionD.getText().toString().trim();
        String correct = correctAnswer.getText().toString().trim();

        if (question.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || correct.isEmpty()) {
            statusView.setText("Please fill in all fields.");
            return;
        }

        // TODO: Save to backend
        statusView.setText("Question Published!");
    }

     */


}
