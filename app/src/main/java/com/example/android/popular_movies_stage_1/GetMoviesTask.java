package com.example.android.popular_movies_stage_1;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

// Followed the walkthrough of @Gill AND from Slack.  His video on Youtube helped me follow the direction
// to implement the working code.

public class GetMoviesTask extends AsyncTask<MainActivityInterface, Void, ArrayList<Movie>>{

    private static final String BASE_URL = "";
    private MainActivityInterface mainActivityInterface;

    @Override
    protected ArrayList<Movie> doInBackground(MainActivityInterface... mainActivityInterfaces){
        mainActivityInterface = mainActivityInterfaces[0];
        ArrayList<Movie> movies = new ArrayList<>();
        URL url = null;
        try {
            url = new URL(BASE_URL + mainActivityInterface.getSortBy());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         try {
            InputStream jsonStream = connection.getInputStream();
            Scanner scanner = new Scanner(jsonStream);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()){
                try {
                    JSONObject json = new JSONObject(scanner.next());
                    JSONArray results = json.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++){
                        JSONObject result = results.getJSONObject(i);
                        String date = result.getString("release_date");
                        String title = result.getString("title");
                        String vote = result.getString("vote_average");
                        String overview = result.getString("overview");
                        String poster = result.getString("poster_path");
                        String id = result.getString("id");
                        movies.add(new Movie(date, title, vote, overview, poster, id));
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                    movies.clear();
                }
            }
        } catch (IOException e){
             e.printStackTrace();
         } finally {
             connection.disconnect();
         }
    } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;

}

@Override
protected void onPostExecute(ArrayList<Movie> movies){
    super.onPostExecute(movies);
    TextView errorMessage = mainActivityInterface.getErrorMessage();
    ProgressBar loadingIndicator = mainActivityInterface.getLoadingIndicator();
    RecyclerView movieGrid = mainActivityInterface.getMovieGrid();
    loadingIndicator.setVisibility(View.INVISIBLE);
    if(movies.isEmpty()){
        errorMessage.setVisibility(View.VISIBLE);
        movieGrid.setVisibility(View.INVISIBLE);
    } else {
        errorMessage.setVisibility(View.INVISIBLE);
        mainActivityInterface.getMovieAdapter().setMovies(movies);
        movieGrid.setVisibility(View.VISIBLE);
    }
    }
}

