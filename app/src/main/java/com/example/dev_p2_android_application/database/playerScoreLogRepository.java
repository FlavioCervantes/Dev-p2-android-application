package com.example.dev_p2_android_application.database;

import android.app.Application;
import android.util.Log;

import com.example.dev_p2_android_application.database.entities.playerScore;


import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class playerScoreLogRepository {

    private final PlayerScoreDAO playerScoreDAO;
    private ArrayList<playerScore> allLogs;
    private static playerScoreLogRepository repository;

    private playerScoreLogRepository(Application application){


        AppDatabase db = AppDatabase.getDatabase(application);
        this.playerScoreDAO = db.playerScoreDAO();

        this.allLogs = (ArrayList<playerScore>) this.playerScoreDAO.getAllRecords();

    }

    public static playerScoreLogRepository getRepository(Application application){

        if(repository != null){
            return repository;
        }
        Future<playerScoreLogRepository> future = AppDatabase.getDatabaseWriteExecutor().submit(

                new Callable<playerScoreLogRepository>() {
                    @Override
                    public playerScoreLogRepository call() throws Exception {
                        return new playerScoreLogRepository(application);
                    }
                }
        );

        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){

            Log.i("DAC_PLAYERSCORELOG", "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public ArrayList<playerScore> getAllLogs() {
        Future<ArrayList<playerScore>> future = AppDatabase.getDatabaseWriteExecutor().submit(
                new Callable<ArrayList<playerScore>>() {

                    @Override
                    public ArrayList<playerScore> call() throws Exception {
                        return (ArrayList<playerScore>) playerScoreDAO.getAllRecords();
                    }
                }
        );

        try{
            return future.get();
        } catch(InterruptedException | ExecutionException e){

            Log.i("DAC_PLAYERSCORELOG", "Problem when getting all GymLogs in the repository");
        }
        return null;
    }

    public void insertPlayerScoreLog(playerScore PlayerScoreLog){
        AppDatabase.getDatabaseWriteExecutor().execute(() ->{

            playerScoreDAO.insert(PlayerScoreLog);
        });
    }
}