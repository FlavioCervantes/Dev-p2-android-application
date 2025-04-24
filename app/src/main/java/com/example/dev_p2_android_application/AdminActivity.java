package com.example.dev_p2_android_application;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dev_p2_android_application.databinding.AdminUiBinding;

public class AdminActivity extends AppCompatActivity {
    AdminUiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = AdminUiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int userId = getIntent().getIntExtra("USER_ID", -1);
        binding.adminWelcome.setText("Welcome, Admin!");

        binding.playGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, PlayGame.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
            finish();
        });
    }
}
