package com.example.dev_p2_android_application.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "activeDirectory")
public class ActiveDirectory {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    // Distinguish between admin/user
    @ColumnInfo(name = "role")
    public String role;

    @ColumnInfo(name = "fullName")
    public String fullName;

}
