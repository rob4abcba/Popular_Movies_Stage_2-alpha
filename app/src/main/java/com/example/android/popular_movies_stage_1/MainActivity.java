package com.example.android.popular_movies_stage_1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popular_movies_stage_1.data.FavoritesProvider;
import com.squareup.picasso.Picasso;


// Followed the walkthrough of @Gill AND from Slack.  His video on Youtube helped me follow the direction
// to implement the working code.

// Updated Code highly inspired and guided by watching Jeriel NG Udacity's Guide
// on Youtube!

public class MainActivity extends AppCompatActivity implements MainActivityInterface {


    private String sortBy;
    private MovieAdapter movieAdapter;
    private RecyclerView movieGrid;
    private TextView errorMessage;
    private ProgressBar loadingIndicator;
    private static final String SORT_BY_MOST_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key=b1d3802dbb5542cb1aaaab6d85d9d5ae";
    private static final String SORT_BY_HIGHEST_RATED = "http://api.themoviedb.org/3/movie/top_rated?api_key=b1d3802dbb5542cb1aaaab6d85d9d5ae";
    private static final String SORT_BY_FAVORITES = "";

    // Stored data for the favorites

    private String[] mPosterPaths;
    private String[] mTitleList;
    private String[] mDescriptionList;
    private double[] mVoteList;
    private String[] mDateList;
    private String[] mIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieGrid = findViewById(R.id.grid_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        movieGrid.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(this);
        movieGrid.setAdapter(movieAdapter);
        errorMessage = findViewById(R.id.empty_view);
        loadingIndicator = findViewById(R.id.loading_indicator);
        sortBy = "http://api.themoviedb.org/3/movie/popular?api_key=b1d3802dbb5542cb1aaaab6d85d9d5ae";
        getMovies();
    }


    public String getSortBy() {return sortBy;}

    public MovieAdapter getMovieAdapter() {return movieAdapter;}

    public TextView getErrorMessage() {return errorMessage;}

    public ProgressBar getLoadingIndicator() {return loadingIndicator;}

    public RecyclerView getMovieGrid() {return movieGrid;}

    public void startDetailActivity (Movie movie) {
        Intent intent = new Intent(this, Details.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sort_most_popular) {
            sortBy = SORT_BY_MOST_POPULAR;
            getMovies();
            return true;
        }

        if (id == R.id.sort_highest_rated) {
            sortBy = SORT_BY_HIGHEST_RATED;
            getMovies();
            return true;
        }

        if (id == R.id.sort_favorites) {
            sortBy = SORT_BY_FAVORITES;
            getFavorites();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        private void getFavorites() {
        Uri uri = FavoritesProvider.CONTENT_URI;
        Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null);

        if (cursor !=null && cursor.moveToFirst()){

            int i = 0;
            int resultsLength = cursor.getCount();
            mIdList = new String [resultsLength];
            mTitleList = new String [resultsLength];
            mPosterPaths = new String [resultsLength];
            mDescriptionList = new String [resultsLength];
            mVoteList = new double [resultsLength];
            mDateList = new String [resultsLength];
            while (!cursor.isAfterLast()) {
                mIdList[i] = cursor
                        // TODO 2: ArrayIndexOutOfBoundsException: length=1; index=1 error
                        // TODO 2: This error occurs in Main Activity Screen when Sort By "Favorites" options is clicked after I have favorited a movie
                        .getString(cursor.getColumnIndex(FavoritesProvider.COLUMN_MOVIE_ID));
                mPosterPaths[i] = cursor
                        .getString(cursor.getColumnIndex(FavoritesProvider.COLUMN_POSTER));
                mDescriptionList[i] = cursor
                        .getString(cursor.getColumnIndex(FavoritesProvider.COLUMN_DESCRIPTION));
                mVoteList[i] = cursor
                        .getDouble(cursor.getColumnIndex(FavoritesProvider.COLUMN_VOTE));
                mDateList[i] = cursor
                        .getString(cursor.getColumnIndex(FavoritesProvider.COLUMN_RELEASE_DATE));

                i++;
                cursor.moveToFirst();
            }
            cursor.close();
            // do I add populateUI or getMovies?  line 213 on master app
            // maybe try adding to turn the other sort by's invisible?
            //populateUI();
            getFavorites();
        } else {

            // create text if there are no favorites

            //TODO: 1 how can I insert the data for favorites into current grid? WHen it is empty
            //TODO: 1 "NullPointerException: Attempt to invoke virtual method 'boolean android.support.v7.widget.RecyclerView$ViewHolder.shouldIgnore()' on a null object reference"
            //TODO: 1 This error occurs when Sort By Favorites when no favorites have been selected.

            TextView noFavorites = new TextView(this);
            noFavorites.setText(getString(R.string.no_favorites));
            noFavorites.setTextSize(18);
            noFavorites.setPadding(30, 30, 0, 0);
            noFavorites.setTextColor(getColor(R.color.noReviews));
            movieGrid.removeAllViews();
            movieGrid.addView(noFavorites);

        }
    }

        private void getMovies() {
        movieGrid.setVisibility(View.INVISIBLE);
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnectedOrConnecting()){
                errorMessage.setVisibility(View.INVISIBLE);
                loadingIndicator.setVisibility(View.VISIBLE);
                new GetMoviesTask().execute(this);
            } else {
                errorMessage.setVisibility(View.VISIBLE);
                loadingIndicator.setVisibility(View.INVISIBLE);
            }
    }

}