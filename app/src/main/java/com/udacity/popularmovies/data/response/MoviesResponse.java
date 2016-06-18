package com.udacity.popularmovies.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by pedro on 09-Jun-16.
 */
public class MoviesResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<MovieResult> results;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("total_results")
    private int total_results;

    public int getPage() {
        return page;
    }

    public List<MovieResult> getResults() {
        return results;
    }


    public class MovieResult {

        @SerializedName("id")
        private String id;

        @SerializedName("backdrop_path")
        private String backdrop_path;

        @SerializedName("poster_path")
        private String poster_path;

        @SerializedName("original_title")
        private String original_title;

        @SerializedName("vote_average")
        private double vote_average;

        @SerializedName("release_date")
        private String release_date;

        @SerializedName("overview")
        private String overview;

        public String getId() {
            return id;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public double getVote_average() {
            return vote_average;
        }

        public String getRelease_date() {
            return release_date;
        }

        public String getOverview() {
            return overview;
        }
    }
}
