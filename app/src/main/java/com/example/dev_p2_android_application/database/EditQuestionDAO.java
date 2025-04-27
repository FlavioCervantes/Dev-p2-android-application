package com.example.dev_p2_android_application.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.dev_p2_android_application.database.entities.EditQuestionDB;

@Dao
public interface EditQuestionDAO {

    @Insert
    void insert(EditQuestionDB question);
}
