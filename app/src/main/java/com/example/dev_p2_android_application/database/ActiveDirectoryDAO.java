package com.example.dev_p2_android_application.database;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

public interface ActiveDirectoryDAO {
    @Insert
    void insertUser(ActiveDirectory user);

    // Ensure password matches with entered username
    @Query("SELECT * FROM active_directory WHERE username = :username AND password = :password LIMIT 1")
    ActiveDirectory login(String username, String password);

    // Determine if login info is for admin/user
    @Query("SELECT role FROM active_directory WHERE username = :username LIMIT 1")
    ActiveDirectory determineUser(String udername);

}
