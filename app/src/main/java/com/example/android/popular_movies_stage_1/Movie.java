package com.example.android.popular_movies_stage_1;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.popular_movies_stage_1.data.FavoritesRoomObject;

// Followed the walkthrough of @Gill AND from Slack.  His video on Youtube helped me follow the direction
// to implement the working code.

public class Movie implements Parcelable {

    private final String Date;
    private final String Title;
    private final String Vote;
    private final String Overview;
    private final String Poster;
    private final String ID;

    public Movie (String date, String title, String vote, String overview, String poster, String id) {
        Date = date;
        Title = title;
        Vote = vote;
        Overview = overview;
        Poster = poster;
        ID = id;
    }

    private Movie(Parcel source){
        Date = source.readString();
        Title = source.readString();
        Vote = source.readString();
        Overview = source.readString();
        Poster = source.readString();
        ID = source.readString();
    }

    public Movie (FavoritesRoomObject favoritesRoomObject){
        Date = getDate();
        Title = getTitle();
        Vote = getVote();
        Overview = getOverview();
        Poster = getPoster();
        ID = getID();
    }

    // Returns Date
    public String getDate() {return Date;}

    // Returns Title
    public String getTitle() {return Title;}

    // Returns Vote
    public String getVote() { return Vote; }

    // Returns Overview
    public String getOverview() {return Overview;}

    // Returns Poster
    public String getPoster() {return Poster;}

    public String getID() {return ID;}

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel (Parcel dest, int flags){
        dest.writeString(Date);
        dest.writeString(Title);
        dest.writeString(Vote);
        dest.writeString(Overview);
        dest.writeString(Poster);
        dest.writeString(ID);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel (Parcel source){return new Movie(source);}

        @Override
        public Movie[] newArray(int size) {return new Movie[size]; }
    };

}