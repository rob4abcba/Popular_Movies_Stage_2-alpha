package com.example.android.popular_movies_stage_1;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

// Updated Code highly inspired and guided by watching Jeriel NG Udacity's Guide
// on Youtube!

public class NetworkUtils {

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/";
    private static final String API_VERSION = "3";
    private static final String MOVIE = "movie";
    private static final String VIDEOS = "videos";
    private static final String REVIEWS = "reviews";
    private static final String API_KEY_PARAM = "api_key";


    public static URL buildMovieURL(String sortType) {
        Uri uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(API_VERSION)
                .appendPath(MOVIE)
                .appendPath(sortType)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildTrailerUrl(String id) {
        Uri uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(API_VERSION)
                .appendPath(MOVIE)
                .appendPath(id)
                .appendPath(VIDEOS)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildReviewUrl(String id) {
        Uri uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(API_VERSION)
                .appendPath(MOVIE)
                .appendPath(id)
                .appendPath(REVIEWS)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}