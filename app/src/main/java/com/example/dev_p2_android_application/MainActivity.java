package com.example.dev_p2_android_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.database.ActiveDirectoryDAO;
import com.example.dev_p2_android_application.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    // TODO: Determine if this is necessary still (Monica)
    int loggedInUserId = -1;
    public static final String TAG = "TRIVIA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_login);

        //TODO: (Monica)
        loginUser();


        if (loggedInUserId == -1) {
            // Redirect to LoginActivity if no user is logged in
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
            finish(); // Close MainActivity to prevent returning to it
            return;
        }

        //imageview setup

        // ImageView setup
        ImageView imageView = findViewById(R.id.TriviaGameLogo);
        imageView.setImageResource(R.drawable.trivia_game_logo);


        // Initialize the database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "active_directory_database")
                .allowMainThreadQueries() // For simplicity, not recommended for production
                .build();

        ActiveDirectoryDAO activeDirectoryDAO = db.activeDirectoryDAO();

        // Use the DAOs to perform database operations
        new Thread(() -> {
            // Example: Insert a new user
            ActiveDirectory user = new ActiveDirectory();
            activeDirectoryDAO.insertUser(user);

            // Get the values of all users and log them
            for (ActiveDirectory activeDirectory : activeDirectoryDAO.getAllUsers()) {
                System.out.println(activeDirectory.username);
            }
        }).start();
    }

    private void loginUser() {
        // TODO: Implement login logic
        // Example: Check if a user is logged in and set loggedInUserId
        // loggedInUserId = <valid user ID> if logged in, otherwise -1
    }
}