package com.example.android.popular_movies_stage_1.data;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.android.popular_movies_stage_1.R;

public class FavoritesDbSingle extends MovieDb {

    private static MovieDb myDatabase;

    private FavoritesDbSingle(Context context){
    }

    public static MovieDb getInstance(Context currContext){
        if(myDatabase == null){
            myDatabase = Room.databaseBuilder(currContext.getApplicationContext(), MovieDb.class, currContext.getString(R.string.favorite_movies_table_name)).build();
        }
        return myDatabase;
    }


    @Override
    public MovieDao movieDao() {
        return myDatabase.movieDao();
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}