package com.example.dev_p2_android_application;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dev_p2_android_application.databinding.UserUiBinding;

public class UserActivity extends AppCompatActivity {
    UserUiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding = UserUiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int userId = getIntent().getIntExtra("USER_ID", -1);

        binding.playGameButtonUser.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, PlayGame.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });
    }
}
