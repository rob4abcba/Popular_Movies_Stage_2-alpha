package com.example.android.popular_movies_stage_1.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

@Dao
@TypeConverters({Converters.class})
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<FavoritesRoomObject>> getAllFavMovies();

    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    List<FavoritesRoomObject> loadAllByIds(int[] movieIds);

    @Query("SELECT * FROM movies WHERE id LIKE :movieId LIMIT 1")
    FavoritesRoomObject findById(int movieId);

    @Query("SELECT * FROM movies WHERE title LIKE :movieTitle LIMIT 1")
    FavoritesRoomObject findByTitle(String movieTitle);

    @Insert
    void insertAll(FavoritesRoomObject... movies);

    @Delete
    void delete(FavoritesRoomObject movie);

}