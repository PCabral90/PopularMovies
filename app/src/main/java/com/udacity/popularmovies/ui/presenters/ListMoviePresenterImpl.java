package com.udacity.popularmovies.ui.presenters;

import android.support.annotation.NonNull;

import com.udacity.popularmovies.data.api.MovieApi;
import com.udacity.popularmovies.data.model.Movie;
import com.udacity.popularmovies.data.response.MoviesResponse;
import com.udacity.popularmovies.ui.fragments.ListMovieView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pedro on 20-Jun-16.
 */
public class ListMoviePresenterImpl {

    private final MovieApi movieApi;
    private final MovieResponseCallback movieResponseCallback;

    private ListMovieView view;

    @Inject
    public ListMoviePresenterImpl(MovieApi movieApi) {
        this.movieApi = movieApi;
        movieResponseCallback = new MovieResponseCallback();
    }

    public void attachView(@NonNull ListMovieView view) {
        this.view = view;
    }

    public void dettachView() {
        this.view = null;
    }

    public void loadPopularMoviesByPage(int page) {
        movieApi.getPopularMovies(page).enqueue(movieResponseCallback);
    }

    public void loadTopRatedMoviesByPage(int page) {
        movieApi.getTopRated(page).enqueue(movieResponseCallback);
    }


    private class MovieResponseCallback implements Callback<MoviesResponse> {

        @Override
        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
            MoviesResponse movieResponse = response.body();

            List<Movie> movies = new ArrayList<>(response.body().getResults().size());

            for (MoviesResponse.MovieResult movieResult : movieResponse.getResults()) {
                movies.add(new Movie(
                        movieResult.getPoster_path(),
                        movieResult.getBackdrop_path(),
                        movieResult.getOriginal_title(),
                        movieResult.getRelease_date(),
                        movieResult.getVote_average(),
                        movieResult.getOverview()
                ));
            }

            if (view != null)
                view.onMoviesLoaded(movies);
        }

        @Override
        public void onFailure(Call<MoviesResponse> call, Throwable t) {
            if (view != null)
                view.onMoviesLoadedError(t);
        }
    }
}
