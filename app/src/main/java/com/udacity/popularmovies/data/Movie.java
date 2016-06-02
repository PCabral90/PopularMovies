package com.udacity.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pcabral on 01-06-2016.
 */
public class Movie implements Parcelable{

    private String poster;

    public Movie(String poster){
        this.poster = poster;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


    //Parcelable Implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster);
    }

    protected Movie(Parcel in) {
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
