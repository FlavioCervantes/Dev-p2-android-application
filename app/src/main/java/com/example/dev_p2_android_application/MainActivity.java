package com.example.dev_p2_android_application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.dev_p2_android_application.database.AppRepository;
import com.example.dev_p2_android_application.database.PlayerScoreDAO;
import com.example.dev_p2_android_application.database.TriviaQuestionsDAO;
import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.database.ActiveDirectoryDAO;
import com.example.dev_p2_android_application.database.AppDatabase;
import com.example.dev_p2_android_application.database.entities.TriviaQuestions;
import com.example.dev_p2_android_application.databinding.ActivityMainBinding;
import com.example.dev_p2_android_application.databinding.AdminUiBinding;
import com.example.dev_p2_android_application.databinding.PlayGameBinding;
import com.example.dev_p2_android_application.databinding.UserUiBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TriviaGame";
    private int loggedInUserId = -1;
    private ActiveDirectory user;
    private TriviaQuestionsDAO questionsDAO;
    private PlayerScoreDAO playerScoreDAO;
    private ActiveDirectoryDAO activeDirectoryDAO;
    private static final int LOGGED_OUT = -1;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.dev_p2_android_application.MAIN_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.dev_p2_android_application.SAVED_INSTANCE_STATE_USERID_KEY";
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(loggedInUserId == -1){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        // Initialize the database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "active_directory_database")
                .allowMainThreadQueries() // For simplicity, not recommended for production
                .build();

        // Access the DAOs
        questionsDAO = db.triviaQuestionDao();
        playerScoreDAO = db.playerScoreDAO();
        activeDirectoryDAO = db.activeDirectoryDAO();

        //Initializing repository
        repository = AppRepository.getRepository(getApplication());

        // Use the DAOs to perform database operations
        new Thread(() -> {
            // Example: Insert a new user
            ActiveDirectory user = new ActiveDirectory();
            activeDirectoryDAO.insertUser(user);

            //Get the values of all users and start the MainActivity
            for(ActiveDirectory activeDirectory : activeDirectoryDAO.getAllUsers()) {
                System.out.println(activeDirectory.username);
            }

            loginUser(savedInstanceState);

            //TODO: maybe I can make this a method database operations
            LiveData<List<ActiveDirectory>> allUsersLiveData = activeDirectoryDAO.getAllUsersLiveData();
            allUsersLiveData.observe(this, activeDirectories -> {
                for(ActiveDirectory activeDirectory : activeDirectories){
                    System.out.println(activeDirectory.username);
                }
            });
            //TODO: Make sure playGameButtonUser is the right button
            Button playButton = findViewById(R.id.playGameButtonUser);
            playButton.setOnClickListener( v -> {
                    Intent intent = new Intent(MainActivity.this, PlayGame.class);
                    intent.putExtra("USER_ID", loggedInUserId);
                    startActivity(intent);
            });
        }).start();
    }

    private void loginUser(Bundle savedInstanceState){
        EditText usernameInput = findViewById(R.id.username);
        Button loginButton = findViewById(R.id.loginButton);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.password),
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.username),LOGGED_OUT);

        if(loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            return;
        }
        // TODO:Create getUserByUserId method in the repository
        LiveData<ActiveDirectory> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(this.user != null){
                if(this.user.isAdmin()){
                    AdminUiBinding adminUiBinding = AdminUiBinding.inflate(getLayoutInflater());
                    setContentView(adminUiBinding.getRoot());
                    //Quit button
                    adminUiBinding.quitGameButton.setOnClickListener(v -> showQuit());
                } else {
                    UserUiBinding userUiBinding = UserUiBinding.inflate(getLayoutInflater());
                    setContentView(userUiBinding.getRoot());
                    //Quit button
                    userUiBinding.quitGameButton.setOnClickListener(v -> showQuit());
                }
                invalidateOptionsMenu();
            }
        });
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY,loggedInUserId);
        updateSharedPreference();
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.password),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.username),loggedInUserId);
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
        //TODO: Make sure there is a logout_menu in strings.xml
        //inflater.inflate(R.menu.logOutButtonUser,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //TODO: Create string for logoutMenuItem, make sure this works for both admin and user
        MenuItem item = menu.findItem(R.id.logOutButtonUser);
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

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }
    private void showQuit() {
        new AlertDialog.Builder(this)
                .setTitle("Quit Game")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("yes", ((dialog, which) -> finishAffinity()))
                .setNegativeButton("No", null)
                .show();
    }
}