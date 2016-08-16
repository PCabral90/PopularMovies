package com.udacity.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pedro on 16-Aug-16.
 */
public class MovieReview implements Parcelable {

    private String author;
    private String content;

    public MovieReview(String author, String content){
        this.author = author;
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);
    }

    public MovieReview() {
    }

    protected MovieReview(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
    }

    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel source) {
            return new MovieReview(source);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };
}
