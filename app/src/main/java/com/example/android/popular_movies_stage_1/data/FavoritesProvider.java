package com.example.android.popular_movies_stage_1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

// Code highly inspired and guided by watching Jeriel NG Udacity's Guide
// on Youtube!

public class FavoritesProvider extends ContentProvider {

    // URI for provider to gain access with

    public static final String PREFIX = "content://";
    public static final String AUTHORITY = "com.example.android.popular_movies_stage_1";
    public static final Uri URI_BASE = Uri.parse(PREFIX + AUTHORITY);
    public static final Uri CONTENT_URI = URI_BASE.buildUpon()
            .appendPath(DbHelper.TABLE_NAME)
            .build();

    // Code to get data from the SQLite Database

    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POSTER = "poster_key";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_VOTE = "rating";
    public static final String COLUMN_RELEASE_DATE = "release_date";

    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(this.getContext());
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long movieId = database.insert(DbHelper.TABLE_NAME, null, values);
        if (movieId > 0){
            Uri result = ContentUris.withAppendedId(uri, movieId);
            getContext().getContentResolver().notifyChange(result, null);
            return result;
        } else {
            return null;
        }

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        cursor = mDbHelper.getReadableDatabase().query(
                DbHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Update not needed for now!");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = mDbHelper.getWritableDatabase();
        if (null == selection){
            selection = "1";
        }
        int deleted = database.delete(DbHelper.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("getType is not needed!");
    }
}
