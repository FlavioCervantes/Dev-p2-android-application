package com.example.dev_p2_android_application.database;

// ********************Application Repository ********************
// This class is responsible for managing data operations and providing a clean API for data access to the rest of the application
// It abstracts the data sources (e.g., local database, remote server) and provides a unified interface for data access
// It uses the DAO (Data Access Object) pattern to interact with the database
// It handles threading and background operations using AsyncTask or ExecutorService
// It provides methods to perform CRUD (Create, Read, Update, Delete) operations on the database entities
// It is a singleton class to ensure that only one instance of the repository exists throughout the application
//

import android.app.Application;
import  android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.dev_p2_android_application.MainActivity;
import com.example.dev_p2_android_application.database.entities.TriviaQuestions;
import com.example.dev_p2_android_application.database.entities.ActiveDirectory;
import com.example.dev_p2_android_application.database.entities.playerScore;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class AppRepository {
    private final TriviaQuestionsDAO triviaQuestionsDAO;
    private final ActiveDirectoryDAO activeDirectoryDAO;
    private final PlayerScoreDAO playerScoreLogDAO;
    private static AppRepository repository;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.triviaQuestionsDAO = db.triviaQuestionDao();
        this.activeDirectoryDAO = db.activeDirectoryDAO();
        this.playerScoreLogDAO = db.playerScoreDAO();
    }

    public static AppRepository getRepository(Application application) {
        if(repository != null){
            return repository;
        }
        Future<AppRepository> future = AppDatabase.getDatabaseWriteExecutor().submit(
                new Callable<AppRepository>() {
                    @Override
                    public AppRepository call() throws Exception {
                        return new AppRepository(application);
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting App Repository.");
        }
        return null;

    }

    // TriviaQuestions operations
    public ArrayList<TriviaQuestions> getAllTriviaQuestions() {
        Future<ArrayList<TriviaQuestions>> future = AppDatabase.getDatabaseWriteExecutor().submit(
                new Callable<ArrayList<TriviaQuestions>>() {
                    @Override
                    public ArrayList<TriviaQuestions> call() throws Exception {
                        return (ArrayList<TriviaQuestions>) triviaQuestionsDAO.getAllQuestions();
                    }
                }
        );

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            // Handle the exception
            Log.i(MainActivity.TAG, "Problem getting all TriviaQuestions in the repository");
        }
        return null;
    }

    // ActiveDirectory operations
    public ArrayList<ActiveDirectory> getAllActiveDirectories() {
        Future<ArrayList<ActiveDirectory>> future = AppDatabase.getDatabaseWriteExecutor().submit(
                new Callable<ArrayList<ActiveDirectory>>() {
                    @Override
                    public ArrayList<ActiveDirectory> call() throws Exception {
                        return (ArrayList<ActiveDirectory>) activeDirectoryDAO.getAllUsers();
                    }
                }
        );

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            // Handle the exception
            Log.i(MainActivity.TAG, "Problem getting all ActiveDirectories in the repository");
            return new ArrayList<>();
        }
    }

    public void insertActiveDirectory(ActiveDirectory directory) {
        new InsertActiveDirectoryAsyncTask(activeDirectoryDAO).execute(directory);
    }

    public LiveData<ActiveDirectory> getUserByUserName(String username) {
        return activeDirectoryDAO.getUserByUserName(username);
    }

    public LiveData<ActiveDirectory> getUserByUserId(int loggedInUserId) {
        return activeDirectoryDAO.getUserByUserId(loggedInUserId);
    }

    private static class InsertActiveDirectoryAsyncTask extends AsyncTask<ActiveDirectory, Void, Void> {
        private final ActiveDirectoryDAO dao;

        InsertActiveDirectoryAsyncTask(ActiveDirectoryDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ActiveDirectory... directories) {
            dao.insertUser(directories[0]);
            return null;
        }
    }

    // PlayerScore operations
    public ArrayList<playerScore> getAllPlayerScores() {
        Future<ArrayList<playerScore>> future = AppDatabase.getDatabaseWriteExecutor().submit(
                new Callable<ArrayList<playerScore>>() {
                    @Override
                    public ArrayList<playerScore> call() throws Exception {
                        return (ArrayList<playerScore>) playerScoreLogDAO.getAllRecords();
                    }
                }
        );

        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            // Handle the exception
            Log.i(MainActivity.TAG, "Problem getting all PlayerScores in the repository");
        }
        return new ArrayList<>();
    }

    public void insertPlayerScore(playerScore scoreLog) {
        new InsertPlayerScoreAsyncTask(playerScoreLogDAO).execute(scoreLog);
    }

    private static class InsertPlayerScoreAsyncTask extends AsyncTask<playerScore, Void, Void> {
        private final PlayerScoreDAO dao;

        InsertPlayerScoreAsyncTask(PlayerScoreDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(playerScore... scoreLogs) {
            dao.insert(scoreLogs[0]);
            return null;
        }
    }
}