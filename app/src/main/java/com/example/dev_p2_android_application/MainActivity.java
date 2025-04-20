package com.example.dev_p2_android_application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.database.ActiveDirectoryDAO;
import com.example.dev_p2_android_application.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private int loggedInUserId = -1;
    private ActiveDirectory user;
    private static final int LOGGED_OUT = -1;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.dev_p2_android_application.MAIN_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.dev_p2_android_application.SAVED_INSTANCE_STATE_USERID_KEY";
    // TODO: Change the repository to whichever we will use.
    private ActivieDirectoryRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "active_directory_database")
                .allowMainThreadQueries() // For simplicity, not recommended for production
                .build();

        // Access the DAO
        ActiveDirectoryDAO activeDirectoryDAO = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "active_directory_database")
                .allowMainThreadQueries() // For simplicity, not recommended for production
                .build()
                .userDao();

        // Use the DAOs to perform database operations
        new Thread(() -> {
            // Example: Insert a new user
            ActiveDirectory user = new ActiveDirectory();
            activeDirectoryDAO.insertUser(user);

            //Get the values of all users and start the MainActivity
            for(ActiveDirectory activeDirectory : activeDirectoryDAO.getAllUsers()) {
                System.out.println(activeDirectory.username);
            }
        }).start();
        }

        private void loginUser(Bundle savedInstanceState){
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name),
                    Context.MODE_PRIVATE);
            loggedInUserId = sharedPreferences.getInt(getString(R.string.app_name),LOGGED_OUT);

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
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.app_name),loggedInUserId);
        sharedPrefEditor.apply();
    }

}
