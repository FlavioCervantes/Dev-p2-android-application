package com.example.dev_p2_android_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.dev_p2_android_application.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private TextView scoreText;
    private TextView timerText;
    private TextView questionText;
    private Button optionOne, optionTwo, optionThree, optionFour;
    private CountDownTimer countDownTimer;
    private static final long TIME = 30000;

    private List<Questions> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    // database declaration
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        // UI setup
        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);
        questionText = findViewById(R.id.questionText);
        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        optionFour = findViewById(R.id.optionFour);

        // Quit Button
        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizActivity.this, PlayGame.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        db = AppDatabase.getInstance(this);

        // Sample questions
        questionList = new ArrayList<>();
        questionList.add(new Questions("What does JVM stand for?",
                new String[]{"Just Virtual Machines", "JVM Visits Mars", "Java Virtual Machine", "Java Virtual Mode"},
                "Java Virtual Machine"));

        questionList.add(new Questions("What is the output: \"I like cake\".equals(\"I like CAKE\");",
                new String[]{"true", "false", "Nothing", "I love cake"},
                "false"));

        loadNextQuestion(); // Start with the first question
    }

    // Launches next question
    private void loadNextQuestion() {
        if (countDownTimer != null) {
            // cancel previous timer
            countDownTimer.cancel();
        }

        // Reached the end of questions --> High Score page
        if (currentQuestionIndex >= questionList.size()) {
            String username = getIntent().getStringExtra("username");

            // save score to db
            db.highScoreDAO().insert(new HighScore(username, score));

            // redirect to High Score page
            Intent intent = new Intent(QuizActivity.this, HighScoreActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        resetButtons();

        // load next question. sets each button with answer
        Questions current = questionList.get(currentQuestionIndex);
        questionText.setText(current.getQuestion());
        optionOne.setText(current.getOptions()[0]);
        optionTwo.setText(current.getOptions()[1]);
        optionThree.setText(current.getOptions()[2]);
        optionFour.setText(current.getOptions()[3]);

        setOptionClickListeners(current);
        startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time Left: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timerText.setText("Out of Time!");
                disableOptionButtons();
                loadNextDelay();
            }
        }
        .start();
    }

    private void checkAnswer(Button selectedButton, String selectedAnswer, String correctAnswer) {
        countDownTimer.cancel();

        if (selectedAnswer.equals(correctAnswer)) {
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
            score++;
            scoreText.setText("Score: " + score);
        } else {
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        }

        disableOptionButtons();
        loadNextDelay();
    }

    private void loadNextDelay() {
        new Handler().postDelayed(() -> {
            currentQuestionIndex++;
            loadNextQuestion();
        }, 1500);
    }

    private void resetButtons() {
        Button[] buttons = {optionOne, optionTwo, optionThree, optionFour};
        for (Button b : buttons) {
            b.setEnabled(true);
            b.setBackgroundColor(ContextCompat.getColor(this, android.R.color.system_control_normal_light));
        }
    }

    private void disableOptionButtons() {
        optionOne.setEnabled(false);
        optionTwo.setEnabled(false);
        optionThree.setEnabled(false);
        optionFour.setEnabled(false);
    }

    private void setOptionClickListeners(Questions question) {
        optionOne.setOnClickListener(v -> checkAnswer(optionOne, optionOne.getText().toString(), question.getCorrectAnswer()));
        optionTwo.setOnClickListener(v -> checkAnswer(optionTwo, optionTwo.getText().toString(), question.getCorrectAnswer()));
        optionThree.setOnClickListener(v -> checkAnswer(optionThree, optionThree.getText().toString(), question.getCorrectAnswer()));
        optionFour.setOnClickListener(v -> checkAnswer(optionFour, optionFour.getText().toString(), question.getCorrectAnswer()));
    }
}
