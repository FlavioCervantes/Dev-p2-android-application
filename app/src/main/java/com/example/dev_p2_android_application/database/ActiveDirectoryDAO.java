package com.example.dev_p2_android_application.database;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Dao;

import com.example.dev_p2_android_application.database.entities.ActiveDirectory;

import java.util.List;

//TODO: Make ActiveDirectory class
@Dao
public interface ActiveDirectoryDAO {
    @Insert
    void insertUser(ActiveDirectory user);

    // Ensure password matches with entered username
    @Query("SELECT * FROM ActiveDirectory WHERE userName = :username AND password = :password LIMIT 1")
    ActiveDirectory login(String username, String password);

    // Determine if login info is for admin/user
    @Query(value = "SELECT role FROM ActiveDirectory WHERE userName = :username LIMIT 1")
    String determineUser(String username);

    //Retrieve all the users
    @Query("SELECT * FROM ActiveDirectory")
    List<ActiveDirectory> getAllUsers();
}
