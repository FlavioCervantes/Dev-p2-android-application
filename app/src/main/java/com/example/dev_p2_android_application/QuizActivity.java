/*
 *@abstract: Controls the quiz question screen of app
 *           Goes off of play_game.xml to display score, timer, question, and answers
 */

package com.example.dev_p2_android_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.dev_p2_android_application.database.AppDatabase;
import com.example.dev_p2_android_application.database.PlayerScoreDAO;
import com.example.dev_p2_android_application.database.TriviaQuestionsDAO;
import com.example.dev_p2_android_application.database.entities.TriviaQuestions;
import com.example.dev_p2_android_application.database.entities.playerScore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Called on by PlayGame.java; Button pressed, user should be sent to questions page
public class QuizActivity extends AppCompatActivity {
    private TriviaQuestionsDAO questionsDAO;
    private List<TriviaQuestions> questionList;
    private int loginID;
    private PlayerScoreDAO playerScoreDAO;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private TextView questionText, scoreText;
    private TextView timerText;
    private Button optionOne, optionTwo, optionThree, optionFour;
    private CountDownTimer countDownTimer;
    private static final long TIME = 30000;
    private TextView timerCountDown;

    // TODO: DETERMINE WHICH ANSWER IS CORRECT TO LINK BUTTONS !!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);
        questionText = findViewById(R.id.questionText);
        //Initializing questionDAO
        AppDatabase db = AppDatabase.getDatabase(getApplication());
        questionsDAO = db.triviaQuestionDao();
        playerScoreDAO = db.playerScoreDAO();
        loginID = getIntent().getIntExtra("User", -1);

        // Displays UI
        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);

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

        startTimer();
        settingUpQuestions();
    }

    private void settingUpQuestions() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            if (questionsDAO.getAllQuestions().isEmpty()) {
                //Asking multiple questions by adding a for loop
                List<TriviaQuestions> newQuestions = new ArrayList<>();
                newQuestions.add(new TriviaQuestions("What character is known as the Pikachu of Nintendo?", "Sonic", "Mario", "Dimitri", "Link", "Mario"));
                newQuestions.add(new TriviaQuestions("What character is known as the Pikachu of Nintendo?", "Sonic", "Mario", "Dimitri", "Link", "Mario"));
                newQuestions.add(new TriviaQuestions("What character is known as the Pikachu of Nintendo?", "Sonic", "Mario", "Dimitri", "Link", "Mario"));
                newQuestions.add(new TriviaQuestions("What character is known as the Pikachu of Nintendo?", "Sonic", "Mario", "Dimitri", "Link", "Mario"));

                for (TriviaQuestions question : newQuestions) {
                    questionsDAO.insert(question);
                }
            }
            questionList = questionsDAO.getAllQuestions();

            runOnUiThread(() -> {
                currentQuestionIndex = 0;
                newQuestions();
            });
        });
    }

    private void newQuestions() {
        if(currentQuestionIndex >= questionList.size()){
            endOfGame();
            return;
        }
        TriviaQuestions current = questionList.get(currentQuestionIndex);
        questionText.setText(current.getType());

        optionOne.setText(current.getOptionOne());
        optionTwo.setText(current.getOptionTwo());
        optionThree.setText(current.getOptionThree());
        optionFour.setText(current.getOptionFour());
        //This timer makes sure each question has a countdown.
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        startTimer();

        View.OnClickListener answerClickListener = view -> {
            Button clicked = (Button)view;
            String selectedAnswer = clicked.getText().toString();
            String correctAnswer = current.getCorrectAnswer();

            checkAnswer(clicked,selectedAnswer,correctAnswer);

            if(selectedAnswer.equals(correctAnswer)){
                score ++;
            }
            scoreText.setText("Score: " + score);
            currentQuestionIndex++;
            newQuestions();
        };

        optionOne.setOnClickListener(answerClickListener);
        optionTwo.setOnClickListener(answerClickListener);
        optionThree.setOnClickListener(answerClickListener);
        optionFour.setOnClickListener(answerClickListener);
    }

    private void endOfGame() {
        int totalQuestions = questionList.size();
        float percentage = ((float)score/totalQuestions) * 100;

        Toast.makeText(this,"Quiz complete! Final score: " + score, Toast.LENGTH_LONG).show();

        int loginID = getIntent().getIntExtra("User", -1);
        if(loginID == -1){
            Toast.makeText(this,"No User id found", Toast.LENGTH_LONG).show();
            return;
        }
        //Check for score
        playerScore score = playerScoreDAO.getRecordByUserId(loginID);
        if(score == null){
            playerScore newScore = new playerScore( 0,String.valueOf(loginID), "0", "0");
            playerScoreDAO.insert(newScore);
        } else {
            //If the player already has a score record it added a count by 1
            score.setGamesPlayed(score.getGamesPlayed()+1);
            //If the player gets an 80% or higher, they win
            if(percentage >= 80){
                score.setWins(String.valueOf(Integer.parseInt(score.getWins())+1));
            } else {
                //else they lose
                score.setLosses(String.valueOf(Integer.parseInt(score.getLosses())+1));
            }
            playerScoreDAO.update(score);
        }
    }

    // Starts timer as soon as question is shown
    private void startTimer() {
        countDownTimer = new CountDownTimer(TIME, 1000) {
            @Override
            public void onTick(long l) {
                timerText.setText("Time Left: " + l/1000);
            }

            @Override
            public void onFinish() {
                timerText.setText("Out of Time!");
                disableOptionButtons();
            }
        };
        countDownTimer.start();
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