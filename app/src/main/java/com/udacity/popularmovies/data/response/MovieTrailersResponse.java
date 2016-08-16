package com.udacity.popularmovies.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pedro on 16-Aug-16.
 */
public class MovieTrailersResponse {

    @SerializedName("id")
    public  int id;

    @SerializedName("results")
    public List<MovieTrailer> results;

    public class MovieTrailer {
        @SerializedName("id")
        public String id;

        @SerializedName("iso_639_1")
        public String iso_639_1;

        @SerializedName("key")
        public String key;

        @SerializedName("name")
        public String name;

        @SerializedName("site")
        public String site;

        @SerializedName("size")
        public int size;

        @SerializedName("type")
        public String type;
    }
}
