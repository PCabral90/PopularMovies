package com.udacity.popularmovies.data.api;

import com.udacity.popularmovies.data.response.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pedro on 09-Jun-16.
 */
public interface MovieApi {

    String ENDPOINT = "http://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("page") int pageNumber);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRated(@Query("page") int pageNumber);

}
