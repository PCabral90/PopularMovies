package com.udacity.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pcabral on 01-06-2016.
 */
public class Movie implements Parcelable {

    private String poster;
    private String backdrop;
    private String title;
    private String release;
    private double rating;
    private String overview;

    public Movie() {
    }

    public Movie(String poster, String backdrop, String title, String release, double rating, String overview) {
        this.poster = poster;
        this.backdrop = backdrop;
        this.title = title;
        this.release = release;
        this.rating = rating;
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease() {
        return release;
    }

    public double getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster);
        dest.writeString(this.backdrop);
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeDouble(this.rating);
        dest.writeString(this.overview);
    }

    protected Movie(Parcel in) {
        this.poster = in.readString();
        this.backdrop = in.readString();
        this.title = in.readString();
        this.release = in.readString();
        this.rating = in.readDouble();
        this.overview = in.readString();
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
