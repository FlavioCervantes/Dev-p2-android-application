package com.example.dev_p2_android_application;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dev_p2_android_application.databinding.ActivityLoginBinding;

import java.util.zip.Inflater;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}