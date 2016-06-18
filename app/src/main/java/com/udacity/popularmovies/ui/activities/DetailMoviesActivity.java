package com.udacity.popularmovies.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.model.Movie;
import com.udacity.popularmovies.ui.fragments.DetailMoviesFragment;

import butterknife.BindView;

/**
 * Created by pedro on 24-May-16.
 */
public class DetailMoviesActivity extends BaseActivity {

    public static final String EXTRA_MOVIE = "com.udacity.popularmovies.extras.EXTRA_MOVIE";

    @BindView(R.id.movie_backdrop_image)
    ImageView backdropImage;

    private Movie movie;

    public static void StartActivity(Context context, Movie movie) {
        Intent detailMovieIntent = new Intent(context, DetailMoviesActivity.class);
        detailMovieIntent.putExtra(DetailMoviesActivity.EXTRA_MOVIE, movie);
        context.startActivity(detailMovieIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        prepareActionBar();
        loadBackdropImage();

        DetailMoviesFragment fragment = DetailMoviesFragment.newInstance(movie, false);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, fragment)
                .commit();
    }

    private void prepareActionBar() {
        if (toolbar != null) {

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setTitle(movie.getTitle());
            }

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void loadBackdropImage() {
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w342/" + movie.getBackdrop())
                .placeholder(R.mipmap.ic_launcher)
                .into(backdropImage);
    }

}
