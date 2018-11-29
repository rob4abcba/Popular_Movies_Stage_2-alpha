package com.example.android.popular_movies_stage_1.data;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {


    private LiveData<List<FavoritesRoomObject>> favorites;


    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        MovieDb database = FavoritesDbSingle.getInstance(this.getApplication());
        favorites = database.movieDao().getAllFavMovies();
    }


    public LiveData<List<FavoritesRoomObject>> getFavorites() {
        return favorites;
    }
}