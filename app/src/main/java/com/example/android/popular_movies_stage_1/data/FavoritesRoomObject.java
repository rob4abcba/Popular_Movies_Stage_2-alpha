package com.example.android.popular_movies_stage_1.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.android.popular_movies_stage_1.Movie;

// Originally Did SQLite DbHelper, now Converting it to Room

@Entity(tableName = "favorites")
public class FavoritesRoomObject implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "poster_path")
    private String poster;

    @ColumnInfo(name = "vote_average")
    private String vote;

    @ColumnInfo(name = "release_date")
    private String date;

    public FavoritesRoomObject() { super(); }

    public FavoritesRoomObject (Movie movie){
        this.id = movie.getID();
        this.title = movie.getTitle();
        this.overview = movie.getOverview();
        this.poster = movie.getPoster();
        this.vote = movie.getVote();
        this.date = movie.getDate();
    }

    public FavoritesRoomObject(String id,
                               String title,
                               String overview,
                               String poster,
                               String vote,
                               String date){
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster = poster;
        this.vote = vote;
        this.date = date;
    }

    protected FavoritesRoomObject(Parcel in) {
        id = in.readString();
        title = in.readString();
        overview = in.readString();
        poster = in.readString();
        vote = in.readString();
        date = in.readString();
    }

    public static final Creator<FavoritesRoomObject> CREATOR = new Creator<FavoritesRoomObject>() {
        @Override
        public FavoritesRoomObject createFromParcel (Parcel in) { return new FavoritesRoomObject(in); }

        @Override
        public FavoritesRoomObject[] newArray(int size) {return new FavoritesRoomObject[size]; }
    };

    public void setId(String newId) { this.id = newId; }
    public String getId() { return id; }

    public void setTitle(String newTitle) {this.title = newTitle; }
    public String getTitle() { return title; }

    public void setOverview(String newOverview) {this.overview = newOverview; }
    public String getOverview() { return overview; }

    public void setPoster(String newPoster) {this.poster = newPoster; }
    public String getPoster() { return poster; }

    public void setVote (String newVote) {this.vote = newVote;}
    public String getVote() { return vote; }

    public void setDate(String newDate) {this.date = newDate; }
    public String getDate() { return date; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel (Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(poster);
        parcel.writeString(vote);
        parcel.writeString(date);
    }
}