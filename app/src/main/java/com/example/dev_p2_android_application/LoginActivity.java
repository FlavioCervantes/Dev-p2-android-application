package com.example.dev_p2_android_application;

import android.content.Context;
import android.content.Intent;

public class LoginActivity {
    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}