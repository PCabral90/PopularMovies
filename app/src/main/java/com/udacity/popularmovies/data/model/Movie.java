package com.udacity.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcabral on 01-06-2016.
 */
public class Movie implements Parcelable {

    private int id;
    private String poster;
    private String backdrop;
    private String title;
    private String release;
    private double rating;
    private String overview;
    private String trailerUrl;
    private List<MovieReview> movieReviews;

    public Movie() {
    }

    public Movie(int id, String poster, String backdrop, String title, String release, double rating, String overview) {
        this.id=id;
        this.poster = poster;
        this.backdrop = backdrop;
        this.title = title;
        this.release = release;
        this.rating = rating;
        this.overview = overview;
        this.setTrailerUrl(null);
        this.setMovieReviews(new ArrayList<MovieReview>());
    }

    public int getId(){return id;}

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

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl( String url){
        trailerUrl=url;
    }

    public List<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.backdrop);
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeDouble(this.rating);
        dest.writeString(this.overview);
        dest.writeString(this.trailerUrl);
        dest.writeTypedList(this.movieReviews);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.poster = in.readString();
        this.backdrop = in.readString();
        this.title = in.readString();
        this.release = in.readString();
        this.rating = in.readDouble();
        this.overview = in.readString();
        this.trailerUrl = in.readString();
        this.movieReviews = in.createTypedArrayList(MovieReview.CREATOR);
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
