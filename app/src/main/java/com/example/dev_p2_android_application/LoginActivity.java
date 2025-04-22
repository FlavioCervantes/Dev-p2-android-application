package com.example.dev_p2_android_application;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}