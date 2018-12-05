package com.example.android.popular_movies_stage_1;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popular_movies_stage_1.data.FavoritesDbSingle;
import com.example.android.popular_movies_stage_1.data.FavoritesRoomObject;
import com.example.android.popular_movies_stage_1.data.FavoritesViewModel;
import com.example.android.popular_movies_stage_1.data.MovieDb;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;


// Followed the walkthrough of @Gill AND from Slack.  His video on Youtube helped me follow the direction
// to implement the working code.

// Updated Code highly inspired and guided by watching Jeriel NG Udacity's Guide
// on Youtube!

// Got help from @Aaron Quaday From Slack!

//TODO: 12: Favorites button doesnt unfavorite
//TODO 13: once a movie is favorited, sort by favorites doesnt display poster but stores data
//TODO 14: Once a movie is favorited, and if user goes back to main, then goes back to same movie, it is not favorited (preserved state) but movie is still stored in DB

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String sortBy;
    private MovieAdapter movieAdapter;
    private RecyclerView movieGrid;
    private TextView errorMessage;
    private TextView noFavoritesView;
    private ProgressBar loadingIndicator;
    private static final String SORT_BY_MOST_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY;
    private static final String SORT_BY_HIGHEST_RATED = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY;
    private static final String SORT_BY_FAVORITES = "";

    private List<FavoritesRoomObject> mFavoriteMovies;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private FavoritesViewModel favoritesViewModel;


    // Stored data for the favorites

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
        noFavoritesView = findViewById(R.id.empty_view_no_favorites);
        loadingIndicator = findViewById(R.id.loading_indicator);
        sortBy = "http://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY;

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        getMovies();
//        setupMainViewModel();
        subscribeToModel();

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
            //subscribeToModel();
            //calls UI to display list of favorites
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void setupMainViewModel(){
//        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
//    }


//
   private void subscribeToModel() {
    favoritesViewModel.getFavorites().observe(this, new Observer<List<FavoritesRoomObject>>() {
        @Override
        public void onChanged(@Nullable List<FavoritesRoomObject> favoritesRoomObjects) {
            ArrayList<Movie> favMovies = new ArrayList<>();

            for (FavoritesRoomObject favoritesRoomObject : favoritesRoomObjects) {
                favMovies.add(new Movie(favoritesRoomObject));
            }
            movieGrid.setVisibility(View.VISIBLE);
            movieAdapter.setMovies(favMovies);
            movieAdapter.notifyDataSetChanged();

            if (favMovies.isEmpty()) {

                // GOT GUIDANCE FROM STEVE (AND) From Slack on how to correctly display when
                // There are no favorites.

                noFavoritesView.setVisibility(View.VISIBLE);
                movieGrid.setVisibility(View.INVISIBLE);
                errorMessage.setVisibility(View.INVISIBLE);
                loadingIndicator.setVisibility(View.INVISIBLE);
            }

        }
    });
//        favoritesViewModel.getFavorites().observe(this, new Observer<List<FavoritesRoomObject>>() {
//            @Override
//            public void onChanged(@Nullable List<FavoritesRoomObject> movies) {
//                mFavoriteMovies = movies;
//                if (mFavoriteMovies.equals(FAVORITE_TYPE)) {
//                    Log.d(LOG_TAG, "Updating list of favorite movies from LiveData in ViewModel");
//                    movieAdapter.setMovies(mMovies);
//                    movieGrid.setAdapter(movieAdapter);
//                }
//            }
//        });
    }

        private void getFavorites() {
            //FavoritesViewModel favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);


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