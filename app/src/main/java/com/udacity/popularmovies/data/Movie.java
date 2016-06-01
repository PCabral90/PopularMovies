package com.udacity.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pcabral on 01-06-2016.
 */
public class Movie implements Parcelable{

    private final String movieId;
    private final String title;
    private final String poster;

    public Movie(String movieId, String title, String poster){
        this.movieId = movieId;
        this.title = title;
        this.poster = poster;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movieId);
        dest.writeString(this.title);
        dest.writeString(this.poster);
    }

    protected Movie(Parcel in) {
        this.movieId = in.readString();
        this.title = in.readString();
        this.poster = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
