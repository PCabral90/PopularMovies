package com.udacity.popularmovies.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.api.MovieApi;
import com.udacity.popularmovies.data.model.Movie;
import com.udacity.popularmovies.data.response.MovieTrailersResponse;
import com.udacity.popularmovies.data.response.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pedro on 23-May-16.
 */
public class DetailMoviesFragment extends BaseFragment {

    private static final String ARG_MOVIE = "ARG_MOVIE";
    private static final String ARG_SHOW_BACKDROP = "ARG_SHOW_BACKDROP";

    @BindView(R.id.movie_backdrop_image)
    ImageView backdropImage;

    @BindView(R.id.movie_poster_image)
    ImageView posterImage;

    @BindView(R.id.movie_title)
    TextView title;

    @BindView(R.id.movie_release_date)
    TextView releaseDate;

    @BindView(R.id.movie_rating)
    TextView rating;

    @BindView(R.id.movie_overview)
    TextView overview;

    @BindView(R.id.movie_trailer_play)
    ImageView movieTrailerPlay;

    @Inject
    MovieApi movieApi;

    private Movie movie;
    private MovieTrailerCallback movieTrailerCallback;

    public static DetailMoviesFragment newInstance(@NonNull Movie movie, boolean showBackdrop) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        args.putBoolean(ARG_SHOW_BACKDROP, showBackdrop);

        DetailMoviesFragment fragment = new DetailMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PopularMoviesApp.getPopularMovieApp(getContext()).getPopularMoviesComponent().inject(this);

        movieTrailerCallback = new MovieTrailerCallback();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movie = getArguments().getParcelable(ARG_MOVIE);

        showBackdropImage(getArguments().getBoolean(ARG_SHOW_BACKDROP));
        showMovieDetails(movie);
    }

    @OnClick(R.id.movie_trailer_play)
    public void onMovieTrailerPlayClick(View view){

        if(movie.getTrailerUrl()==null || movie.getTrailerUrl().isEmpty()){
            movieApi.getMovieTrailer(movie.getId()).enqueue(movieTrailerCallback);
        }else{
            sendIntentToPlayVideo(movie.getTrailerUrl());
        }
    }

    private void sendIntentToPlayVideo(String url){
        Intent showVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        Intent chooserIntent = Intent.createChooser(showVideoIntent, "Play Trailer");
        startActivity(chooserIntent);
    }

    public void showBackdropImage(boolean show){
        backdropImage.setVisibility(show? View.VISIBLE: View.GONE);
    }

    private void showMovieDetails(Movie movie) {
        showImageFromUrl(backdropImage, movie.getBackdrop());
        showImageFromUrl(posterImage, movie.getPoster());

        title.setText(movie.getTitle());
        releaseDate.setText(movie.getRelease());
        rating.setText(getResources().getString(R.string.movie_rating, movie.getRating(), 10));
        overview.setText(movie.getOverview());
    }

    private void showImageFromUrl(ImageView image, String url) {
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w342/" + url)
                .placeholder(R.mipmap.ic_launcher)
                .into(image);
    }


    private class MovieTrailerCallback implements Callback<MovieTrailersResponse> {

        @Override
        public void onResponse(Call<MovieTrailersResponse> call, Response<MovieTrailersResponse> response) {
            MovieTrailersResponse movieTrailerResponse = response.body();

            for (MovieTrailersResponse.MovieTrailer movieTrailerResult : movieTrailerResponse.results) {
                if(movieTrailerResult.type.equals("Trailer") && movieTrailerResult.site.equals("YouTube")){
                    movie.setTrailerUrl("https://www.youtube.com/watch?v="+movieTrailerResult.key);
                    break;
                }
            }

            sendIntentToPlayVideo(movie.getTrailerUrl());
        }

        @Override
        public void onFailure(Call<MovieTrailersResponse> call, Throwable t) {
        }
    }
}
