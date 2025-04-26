package com.example.dev_p2_android_application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.dev_p2_android_application.database.AppRepository;
import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.databinding.AdminUiBinding;
import com.example.dev_p2_android_application.databinding.UserUiBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TriviaGame";
    private int loggedInUserId = -1;
    private ActiveDirectory user;
    private static final int LOGGED_OUT = -1;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.dev_p2_android_application.MAIN_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.dev_p2_android_application.SAVED_INSTANCE_STATE_USERID_KEY";
    private AppRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = AppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
    }



    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.password),
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.username), LOGGED_OUT);
        Log.d(TAG, "SharedPreferences loggedInUserId: " + loggedInUserId);

        if (loggedInUserId == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
            Log.d(TAG, "SavedInstanceState loggedInUserId: " + loggedInUserId);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
            Log.d(TAG, "Intent loggedInUserId: " + loggedInUserId);
        }
        if (loggedInUserId == LOGGED_OUT) {
            Log.d(TAG, "No valid loggedInUserId found. Redirecting to LoginActivity.");
            startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            finish();
            return;
        }

        Log.d(TAG, "Final loggedInUserId: " + loggedInUserId);

        LiveData<ActiveDirectory> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            Log.d(TAG, "Fetched User: " + (user != null ? user.toString() : "null"));
            if (this.user != null) {
                if (this.user.isAdmin()) {
                    Log.d(TAG, "User is admin. Loading Admin UI.");
                    AdminUiBinding adminUiBinding = AdminUiBinding.inflate(getLayoutInflater());
                    setContentView(adminUiBinding.getRoot());
                } else {
                    Log.d(TAG, "User is not admin. Loading User UI.");
                    UserUiBinding userUiBinding = UserUiBinding.inflate(getLayoutInflater());
                    setContentView(userUiBinding.getRoot());
                }
            } else {
                Log.e(TAG, "User not found in database. Redirecting to LoginActivity.");
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
                finish();
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
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                getString(R.string.password),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.username), loggedInUserId);
        boolean success = sharedPrefEditor.commit(); // Use commit() to check success
        if (success) {
            Log.d(TAG, "SharedPreferences updated successfully: loggedInUserId = " + loggedInUserId);
        } else {
            Log.e(TAG, "Failed to update SharedPreferences.");
        }
    }

}