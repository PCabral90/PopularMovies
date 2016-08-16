package com.udacity.popularmovies.ui.presenters;

import android.support.annotation.NonNull;

import com.udacity.popularmovies.data.api.MovieApi;
import com.udacity.popularmovies.data.model.Movie;
import com.udacity.popularmovies.data.model.MovieReview;
import com.udacity.popularmovies.data.response.MovieReviewsResponse;
import com.udacity.popularmovies.data.response.MovieTrailersResponse;
import com.udacity.popularmovies.data.response.MoviesResponse;
import com.udacity.popularmovies.ui.fragments.ListMovieView;

import java.io.IOException;
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
        public void onFailure(Call<MoviesResponse> call, Throwable t) {
            if (view != null)
                view.onMoviesLoadedError(t);
        }

        @Override
        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
            MoviesResponse movieResponse = response.body();

            List<Movie> movies = new ArrayList<>(response.body().getResults().size());

            for (MoviesResponse.MovieResult movieResult : movieResponse.getResults()) {

                Movie movie = new Movie(
                        movieResult.getId(),
                        movieResult.getPoster_path(),
                        movieResult.getBackdrop_path(),
                        movieResult.getOriginal_title(),
                        movieResult.getRelease_date(),
                        movieResult.getVote_average(),
                        movieResult.getOverview()
                );
                movies.add(movie);
            }

            if (view != null)
                view.onMoviesLoaded(movies);
        }
    }

    private class MovieTrailerResponseCallback implements Callback<MovieTrailersResponse> {

        private Movie movie;

        public MovieTrailerResponseCallback(Movie movie){
            this.movie = movie;
        }

        @Override
        public void onResponse(Call<MovieTrailersResponse> call, Response<MovieTrailersResponse> response) {
            if(response.body()==null){
                view.onMoviesLoadedError(new Throwable());
                return;
            }

            for (MovieTrailersResponse.MovieTrailer movieTrailerResult : response.body().results) {
                if (movieTrailerResult.type.equals("Trailer") && movieTrailerResult.site.equals("YouTube")) {
                    movie.setTrailerUrl( "https://www.youtube.com/watch?v=" + movieTrailerResult.key);
                }
            }
        }

        @Override
        public void onFailure(Call<MovieTrailersResponse> call, Throwable t) {
            if (view != null)
                view.onMoviesLoadedError(t);
        }
    }

    private class MovieReviewResponseCallback implements Callback<MovieReviewsResponse> {

        private Movie movie;

        public MovieReviewResponseCallback(Movie movie){
            this.movie = movie;
        }

        @Override
        public void onResponse(Call<MovieReviewsResponse> call, Response<MovieReviewsResponse> response) {
            List<MovieReview> movieReviews = new ArrayList<>();

            if(response.body()==null){
                view.onMoviesLoadedError(new Throwable());
                return;
            }

            for (MovieReviewsResponse.MovieReview movieReviewResult : response.body().results) {
                movieReviews.add(new MovieReview(movieReviewResult.author, movieReviewResult.content));
            }

            movie.setMovieReviews(movieReviews);
        }

        @Override
        public void onFailure(Call<MovieReviewsResponse> call, Throwable t) {
            if (view != null)
                view.onMoviesLoadedError(t);
        }
    }


}
