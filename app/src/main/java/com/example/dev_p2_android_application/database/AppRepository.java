package com.example.dev_p2_android_application.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.dev_p2_android_application.MainActivity;
import com.example.dev_p2_android_application.database.entities.ActiveDirectory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private final TriviaQuestionsDAO questionsDAO;
    private final ActiveDirectoryDAO activeDirectoryDAO;
    private final PlayerScoreDAO playerScoreDAO;
    private static AppRepository repository;

    private AppRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.questionsDAO = db.triviaQuestionDao();
        this.activeDirectoryDAO = db.activeDirectoryDAO();
        this.playerScoreDAO = db.playerScoreDAO();
    }
    public static AppRepository getRepository(Application application) {
        if(repository != null){
            return repository;
        }
        Future<AppRepository> future = AppDatabase.getDatabaseWriteExecutor().submit(
                new Callable<AppRepository>(){
                    @Override
                    public AppRepository call() throws Exception {
                        return new AppRepository(application);
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting AppRepository.");
        }
        return null;
    }

    public LiveData<ActiveDirectory> getUserByUserName(String username) {
        return activeDirectoryDAO.getUserByUserName(username);
    }

    public LiveData<ActiveDirectory> getUserByUserId(int loggedInUserId) {
        return activeDirectoryDAO.getUserByUserId(loggedInUserId);
    }
}
