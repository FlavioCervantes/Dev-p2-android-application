package com.example.dev_p2_android_application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHolder> {
    private List<HighScore> highScoreList;

    public HighScoreAdapter(List<HighScore> highScores) {
        this.highScoreList = highScores;
    }

//    @NonNull
    @Override
    public ViewHolder onCreateViewHolder ( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scores_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        HighScore score = highScoreList.get(pos);
        holder.userTextView.setText(score.username);
        holder.scoreTextView.setText("Score: " + score.score);
    }

    // gets size of list
    @Override
    public int getItemCount() {
        return highScoreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userTextView;
        TextView scoreTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            userTextView = itemView.findViewById(R.id.usernameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }
}
