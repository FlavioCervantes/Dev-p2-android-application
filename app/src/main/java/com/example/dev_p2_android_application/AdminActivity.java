package com.example.dev_p2_android_application;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dev_p2_android_application.database.ActiveDirectoryRepository;

public class AdminActivity extends AppCompatActivity{


    private EditText questionInput, optionA, optionB, optionC, optionD, correctAnswer;
    private Button publishBtn;
    private TextView statusView;
    public String playGame;
    public String checkScore;
    public void quitGame(){

    }

    public ActiveDirectoryRepository repository;

    @SuppressLint("WrongViewCast")
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
        checkScore = String.valueOf(findViewById(R.id.highScoreButton));
        quitGame() = findViewById(R.id.quitButton);

        questionInput = findViewById(R.id.editTextQuestion);
        optionA = findViewById(R.id.editTextOptionA);
        optionB = findViewById(R.id.editTextOptionB);
        optionC = findViewById(R.id.editTextOptionC);
        optionD = findViewById(R.id.editTextOptionD);
        correctAnswer = findViewById(R.id.editTextCorrectAnswer);
        publishBtn = findViewById(R.id.buttonPublish);
        statusView = findViewById(R.id.statusText);

        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishQuestion();
            }
        });
    }

    private boolean isAdminAuthenticated() {
        // Dummy check, replace with real authentication (e.g., FirebaseAuth or API check)
        return true;
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

}
