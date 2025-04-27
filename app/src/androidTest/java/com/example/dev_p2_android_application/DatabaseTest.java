package com.example.dev_p2_android_application;

import static org.junit.Assert.assertNotNull;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;

import com.example.dev_p2_android_application.database.AppDatabase;

import org.junit.Test;

//Flavios Database Test

public class DatabaseTest {
    @Test
    public void testDatabaseInitialization() {
        // Get the application context
        Context context = ApplicationProvider.getApplicationContext();
        // Initialize the database
        AppDatabase db = AppDatabase.getDatabase((Application) context);

        // Verify that the database instance is not null
        assertNotNull(db);
    }

    @Test
    public void testDatabaseCreation() {
        // Get the application context
        Context context = ApplicationProvider.getApplicationContext();
        // Initialize the database
        AppDatabase db = AppDatabase.getDatabase((Application) context);

        // Verify that the database instance is not null
        assertNotNull(db);
    }
    @Test
    public void testDatabaseDestruction() {
        // Get the application context
        Context context = ApplicationProvider.getApplicationContext();
        // Initialize the database
        AppDatabase db = AppDatabase.getDatabase((Application) context);

        // Verify that the database instance is not null
        assertNotNull(db);

        // Close the database
        db.close();
    }

}