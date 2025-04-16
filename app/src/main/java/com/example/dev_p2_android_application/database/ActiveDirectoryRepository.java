package com.example.dev_p2_android_application.database;


import android.app.Application;
import com.example.dev_p2_android_application.database.ActiveDirectoryDAO;
import com.example.dev_p2_android_application.database.TriviaGameDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: Make ActiveDirectory class

public class ActiveDirectoryRepository {

    private final ActiveDirectoryDAO activeDirectoryDAO;
    private final ExecutorService executorService;

    public ActiveDirectoryRepository (Application application) {
        TriviaGameDatabase db = TriviaGameDatabase.getDatabase(application);
        activeDirectoryDAO = db.activeDirectoryDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    // TODO: create ActiveDirectoryClass
    // Insert new users
    public void insertUser(ActiveDirectory user) {
        executorService.execute(() -> activeDirectoryDAO.insertUser(user));
    }

    public ActiveDirectory login (String username, String password) {
        return activeDirectoryDAO.login(username, password);
    }

    public String determineRole (String username) {
        return activeDirectoryDAO.determineRole(username);
    }

    public List<ActiveDirectory> getAllUsers() {
        return activeDirectoryDAO.getAllUsers();
    }
}
