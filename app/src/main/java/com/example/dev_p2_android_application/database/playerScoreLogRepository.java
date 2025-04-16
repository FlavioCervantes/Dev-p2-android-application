package com.example.dev_p2_android_application.database;

import android.app.Application;
import android.util.Log;

import com.example.dev_p2_android_application.PlayerScoreDAO;
import com.example.dev_p2_android_application.database.entities.playerScoreLog;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class playerScoreLogRepository {

    private final PlayerScoreDAO playerScoreDAO;
    private ArrayList<playerScoreLog> allLogs;
    private static playerScoreLogRepository repository;

    private playerScoreLogRepository(Application application){


        playerScoreDatabase db = playerScoreDatabase.getDatabase(application);
        this.playerScoreDAO = db.playerScoreDAO();

        this.allLogs = (ArrayList<playerScoreLog>) this.playerScoreDAO.getAllRecords();

    }

    public static playerScoreLogRepository getRepository(Application application){

        if(repository != null){
            return repository;
        }
        Future<playerScoreLogRepository> future = playerScoreDatabase.databaseWriteExecutor.submit(

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

    public ArrayList<playerScoreLog> getAllLogs() {
        Future<ArrayList<playerScoreLog>> future = playerScoreDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<playerScoreLog>>() {

                    @Override
                    public ArrayList<playerScoreLog> call() throws Exception {
                        return (ArrayList<playerScoreLog>) playerScoreDAO.getAllRecords();
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

    public void insertPlayerScoreLog(playerScoreLog PlayerScoreLog){
        playerScoreDatabase.databaseWriteExecutor.execute(() ->{

            playerScoreDAO.insert(PlayerScoreLog);
        });
    }
}