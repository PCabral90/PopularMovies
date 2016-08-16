package com.udacity.popularmovies.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pedro on 16-Aug-16.
 */
public class MovieReviewsResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("page")
    public int page;

    @SerializedName("results")
    public List<MovieReview> results;

    public class MovieReview {

        @SerializedName("id")
        public String id;

        @SerializedName("author")
        public String author;

        @SerializedName("content")
        public String content;

        @SerializedName("url")
        public String url;
    }
}
