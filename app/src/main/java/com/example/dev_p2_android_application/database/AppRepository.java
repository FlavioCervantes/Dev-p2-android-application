package com.example.dev_p2_android_application.database;

import android.app.Application;
import  android.os.AsyncTask;
import android.util.Log;

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

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.triviaQuestionsDAO = db.triviaQuestionDao();
        this.activeDirectoryDAO = db.activeDirectoryDAO();
        this.playerScoreLogDAO = db.playerScoreDAO();
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


    public void insertTriviaQuestion(TriviaQuestions question) {
        new InsertTriviaQuestionAsyncTask(triviaQuestionsDAO).execute(question);
    }

    private static class InsertTriviaQuestionAsyncTask extends AsyncTask<TriviaQuestions, Void, Void> {
        private final TriviaQuestionsDAO dao;

        InsertTriviaQuestionAsyncTask(TriviaQuestionsDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TriviaQuestions... questions) {
            dao.insert(questions[0]);
            return null;
        }
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


