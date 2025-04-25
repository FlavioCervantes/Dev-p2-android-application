package com.example.dev_p2_android_application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bumptech.glide.Glide;
import com.example.dev_p2_android_application.database.AppDatabase;

public class HighScoreActivity extends AppCompatActivity {

    // Needed to keep high scores
    private RecyclerView scoreRecycler;
    private HighScoreAdapter adapter;
    private AppDatabase db;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        scoreRecycler = findViewById(R.id.scoresRecyclerView);
        scoreRecycler.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        // LogCat shows app crashing due to running queries on main thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<HighScore> topScores = db.highScoreDAO().getHighFiveScores();
            runOnUiThread(() -> {
                adapter = new HighScoreAdapter(topScores);
                scoreRecycler.setAdapter(adapter);
            });
        });

        // QUIT button in upper right hand corner
        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(v -> finish());

        //Glide yay gif
        /*ImageView yayImage  = findViewById(R.id.celebrate);
        Glide.with(this)
                .asGif()
                .load(R.drawable.yay)
                .into(yayImage);*/
    }
}
