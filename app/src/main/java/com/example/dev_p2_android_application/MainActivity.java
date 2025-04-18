package com.example.dev_p2_android_application;

import android.content.Intent;
import android.os.Bundle;
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

        //TODO: (Monica)
        loginUser();

        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

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

    private void loginUser() {
        //TODO: create login method (Monica)
    }
}
