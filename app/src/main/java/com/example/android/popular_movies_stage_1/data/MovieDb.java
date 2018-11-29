package com.example.android.popular_movies_stage_1.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;


@Database(entities = {FavoritesRoomObject.class}, version = 1)
@TypeConverters({Converters.class})
public  abstract class MovieDb extends RoomDatabase {
    public abstract MovieDao movieDao();
}