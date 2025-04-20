package com.example.dev_p2_android_application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.dev_p2_android_application.database.ActiveDirectoryRepository;
import com.example.dev_p2_android_application.database.PlayerScoreDAO;
import com.example.dev_p2_android_application.database.TriviaQuestionsDAO;
import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.database.ActiveDirectoryDAO;
import com.example.dev_p2_android_application.database.AppDatabase;
import com.example.dev_p2_android_application.database.entities.TriviaQuestions;
import com.example.dev_p2_android_application.database.entities.playerScore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int loggedInUserId = -1;
    private ActiveDirectory user;
    private TriviaQuestionsDAO questionsDAO;
    private PlayerScoreDAO playerScoreDAO;
    private static final int LOGGED_OUT = -1;
    private int score = 0;
    private int currentQuestionIndex = 0;
    private List<TriviaQuestions> questionList;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.dev_p2_android_application.MAIN_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.dev_p2_android_application.SAVED_INSTANCE_STATE_USERID_KEY";
    // TODO: Change the repository to whichever we will use.
    private ActiveDirectoryRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "active_directory_database")
                .allowMainThreadQueries() // For simplicity, not recommended for production
                .build();

        // Access the DAOs
        ActiveDirectoryDAO activeDirectoryDAO = db.ActiveDirectoryDao();
        /* Line 53 might be a better way to access the activeDirectory DAO
        ActiveDirectoryDAO activeDirectoryDAO = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "active_directory_database")
                .allowMainThreadQueries() // For simplicity, not recommended for production
                .build()
                .userDao();
         */
        //Initializing repository
        repository = new ActiveDirectoryRepository(getApplication());

        // Use the DAOs to perform database operations
        new Thread(() -> {
            // Example: Insert a new user
            ActiveDirectory user = new ActiveDirectory();
            activeDirectoryDAO.insertUser(user);

            //Get the values of all users and start the MainActivity
            for(ActiveDirectory activeDirectory : activeDirectoryDAO.getAllUsers()) {
                System.out.println(activeDirectory.username);
            }
            settingUpQuestions();
        }).start();
    }
    private void loginUser(Bundle savedInstanceState){
            //TODO: create string value for preference_file_key
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE);
            //TODO: create string value for preference_userId_key
            loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),LOGGED_OUT);

            if(loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
                loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
            }
            if(loggedInUserId == LOGGED_OUT){
                loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);
            }
            if(loggedInUserId == LOGGED_OUT){
                return;
            }
            // TODO:Create getUserByUserId method in the repository
            LiveData<ActiveDirectory> userObserver = repository.getUserByUserId(loggedInUserId);
            userObserver.observe(this, user -> {
                this.user = user;
                if(this.user != null){
                    invalidateOptionsMenu();
                }
            });
    }
    @Override
    protected void onSavedInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY,loggedInUserId);
        updateSharedPreference();
    }

    private void updateSharedPreference() {
        //TODO: The two errors here will be fixed after the todo from line 67
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key),loggedInUserId);
        sharedPrefEditor.apply();
    }

    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID,userId);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        //TODO: Create string logout_menu in strings.xml
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //TODO: Create string for logoutMenuItem
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);

        if(user == null){
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID,loggedInUserId);
        //TODO: create string for loginActivity in strings.xml
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void settingUpQuestions() {
        if (questionsDAO.getAllQuestions().isEmpty()) {
            //Asking multiple questions by adding a for loop
            List<TriviaQuestions> newQuestions = new ArrayList<>();
            newQuestions.add(new TriviaQuestions("What character is known as the Pikachu of Nintendo?", "Sonic", "Mario", "Dimitri", "Link", "Mario"));
            newQuestions.add(new TriviaQuestions("What character is known as the Pikachu of Nintendo?", "Sonic", "Mario", "Dimitri", "Link", "Mario"));
            newQuestions.add(new TriviaQuestions("What character is known as the Pikachu of Nintendo?", "Sonic", "Mario", "Dimitri", "Link", "Mario"));
            newQuestions.add(new TriviaQuestions("What character is known as the Pikachu of Nintendo?", "Sonic", "Mario", "Dimitri", "Link", "Mario"));

            for (TriviaQuestions question : newQuestions) {
                TriviaQuestionsDAO.insert(question);
            }
            questionList = questionsDAO.getAllQuestions();
            currentQuestionIndex = 0;
            newQuestions();
        }
    }

    private void newQuestions() {
        if(currentQuestionIndex >= questionList.size()){
            endOfGame();
            return;
        }
        TriviaQuestions current = questionList.get(currentQuestionIndex);
        //TODO: rename and add questiontextview
        questionTextView.setText(current.getType());
        View.OnClickListener answerClickListener = view -> {
            Button clicked = (Button)view;
            if(clicked.getText().toString().equals(current.getCorrectAnswer())){
                score ++;
            }
            //TODO: rename and add scoreTextView here
            scoreTextView.setText("Score: " + score);
            currentQuestionIndex++;
            newQuestions();
        };
    }

    private void endOfGame() {
        int totalQuestions = questionList.size();
        float percentage = ((float)score/totalQuestions) * 100;

        Toast.makeText(this,"Quiz complete! Final score: " + score, Toast.LENGTH_LONG).show();
        //Check for score
        //TODO: Get player score loginID from playerScore
        playerScore score = playerScoreDAO.getAllRecords(loginID);
        if(score == null){
            playerScore newScore = new playerScore(0,loginID,"0", "0");
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
            //TODO:Add a method to update playerscore
            playerScoreDAO.update(score);
        }
    }
}