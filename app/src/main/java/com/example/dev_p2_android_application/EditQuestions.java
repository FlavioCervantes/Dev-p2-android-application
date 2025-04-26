package com.example.dev_p2_android_application;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.dev_p2_android_application.database.AppDatabase;
import com.example.dev_p2_android_application.database.entities.EditQuestionDB;
import com.example.dev_p2_android_application.database.entities.TriviaQuestions;
import com.example.dev_p2_android_application.databinding.ActivityMainBinding;

public class EditQuestions extends AppCompatActivity {

    AppDatabase db;
    private EditText questionInput, optionA, optionB, optionC, optionD, correctAnswer;
    private Button publishBtn;
    private TextView statusView;
    private ActivityMainBinding binding;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_questions);

        questionInput = (EditText) findViewById(R.id.editTextQuestion);
        optionA = (EditText) findViewById(R.id.editTextOptionA);
        optionB = (EditText) findViewById(R.id.editTextOptionB);
        optionC = (EditText) findViewById(R.id.editTextOptionC);
        optionD = (EditText) findViewById(R.id.editTextOptionD);
        correctAnswer = (EditText) findViewById(R.id.editTextCorrectAnswer);
        publishBtn = findViewById(R.id.buttonPublish);

         db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "EditQuestionsDB").build();




        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                publishQuestion();

                finish();
            }
        });



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
        else {

            EditQuestionDB newEditQuestionDB = new EditQuestionDB(question, a, b, c, d, correct);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    db.EditQuestionDAO().insert(newEditQuestionDB);
                }
            });

        }

        Toast.makeText(EditQuestions.this, "Question saved!", Toast.LENGTH_SHORT).show();
    }

}
