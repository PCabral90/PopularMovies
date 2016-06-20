package com.udacity.popularmovies.ui.fragments;

import com.udacity.popularmovies.data.model.Movie;

import java.util.List;

/**
 * Created by pedro on 20-Jun-16.
 */
public interface ListMovieView {
    void onMoviesLoaded(List<Movie> movies);
    void onMoviesLoadedError(Throwable t);
}
