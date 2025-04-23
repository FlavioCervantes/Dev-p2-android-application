package com.example.dev_p2_android_application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.dev_p2_android_application.database.AppRepository;
import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.databinding.ActivityMainBinding;
import com.example.dev_p2_android_application.databinding.AdminUiBinding;
import com.example.dev_p2_android_application.databinding.UserUiBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppRepository repository;
    private int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(v -> {
            String username = binding.username.getText().toString();
            loginUser(username);
        });
    }

    private void loginUser(String username) {
        if(username.isEmpty()){
            toastmaker("Username should not be blank");
            return;
        }

        LiveData<ActiveDirectory> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null){
                Log.d("LoginActivity", "User found: " + user.getUsername());
                String password = binding.password.getText().toString();

                if(password.equals(user.getPassword())){
                    loggedInUserId = user.getId();
                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.password).toString(),
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(getString(R.string.username), loggedInUserId);
                    editor.apply();

                    if(user.getRole().equalsIgnoreCase("admin")){
                        AdminUiBinding adminUiBinding = AdminUiBinding.inflate(getLayoutInflater());
                        setContentView(adminUiBinding.getRoot());
                        adminUiBinding.playGameButton.setOnClickListener(v -> {
                            Intent intent = new Intent(LoginActivity.this, PlayGame.class);
                            intent.putExtra("USER_ID", loggedInUserId);
                            startActivity(intent);
                        });
                    } else if(user.getRole().equalsIgnoreCase("user")){
                        UserUiBinding userUiBinding = UserUiBinding.inflate(getLayoutInflater());
                        setContentView(userUiBinding.getRoot());
                        userUiBinding.playGameButtonUser.setOnClickListener(v -> {
                            Intent intent = new Intent(LoginActivity.this, PlayGame.class);
                            intent.putExtra("USER_ID", loggedInUserId);
                            startActivity(intent);
                        });
                    }
                    finish();
                } else {
                    toastmaker("Invalid password");
                }
            } else {
                toastmaker(String.format("User %s is not a valid username.", username));
                binding.username.setSelection(0);
            }
        });
    }

    private void toastmaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}