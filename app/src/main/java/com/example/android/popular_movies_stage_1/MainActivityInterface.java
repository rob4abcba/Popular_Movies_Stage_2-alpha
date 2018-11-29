package com.example.android.popular_movies_stage_1;

import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

// Followed the walkthrough of @Gill AND from Slack.  His video on Youtube helped me follow the direction
// to implement the working code.

 public interface MainActivityInterface {
    String getSortBy();
    MovieAdapter getMovieAdapter();
    TextView getErrorMessage();
    ProgressBar getLoadingIndicator();
    RecyclerView getMovieGrid();
    void startDetailActivity(Movie movie);
}