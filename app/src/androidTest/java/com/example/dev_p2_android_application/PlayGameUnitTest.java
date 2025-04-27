package com.example.dev_p2_android_application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class PlayGameUnitTest {

    //Raquel's Test 1
    @Test
    public void testSetupAdminUI() {
        // Create an intent with the admin role
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.example.dev_p2_android_application", "com.example.dev_p2_android_application.PlayGame");
        intent.putExtra("USER_ROLE", "admin");
        intent.putExtra("USER_ID", 1);


        // Launch the activity with the intent
        try (ActivityScenario<PlayGame> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                // Verify TextView
                TextView welcomeText = activity.findViewById(R.id.adminWelcome);
                assertNotNull("Admin welcome text is null", welcomeText);
                assertEquals("Welcome, Admin!", welcomeText.getText().toString());

                // Verify Buttons
                assertNotNull("Play Game button is null", activity.findViewById(R.id.playGameButton));
                assertNotNull("High Score button is null", activity.findViewById(R.id.highScoreButton));
                assertNotNull("Log Out button is null", activity.findViewById(R.id.logOutButton));
            });
        }
    }
    //Raquel's Test 2
    @Test

    public void testSetupUserUI() {
        // Create an intent with the user role
        Intent intent = new Intent();
        intent.setClassName("com.example.dev_p2_android_application", "com.example.dev_p2_android_application.PlayGame");
        intent.putExtra("USER_ROLE", "user");
        intent.putExtra("USER_ID", 1);

        // Launch the activity with the intent
        try (ActivityScenario<PlayGame> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                // Verify TextView
                TextView welcomeText = activity.findViewById(R.id.userWelcome);
                assertNotNull("User welcome text is null", welcomeText);
                assertEquals("Welcome, User!", welcomeText.getText().toString());

                // Verify Buttons
                assertNotNull("Play Game button is null", activity.findViewById(R.id.playGameButtonUser));
                assertNotNull("High Score button is null", activity.findViewById(R.id.highScoreButtonUser));
                assertNotNull("Log Out button is null", activity.findViewById(R.id.logOutButtonUser));
            });
        }
    }







}