package com.udacity.popularmovies.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.model.Movie;
import com.udacity.popularmovies.ui.adapters.MoviesAdapter;
import com.udacity.popularmovies.ui.fragments.DetailMoviesFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Retrofit;

public class ListMoviesActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.movies_detail_container)
    protected View detailContainer;

    private boolean twoPaneMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        if (detailContainer != null) {
            twoPaneMode = true;
            showMovieDetailFragment(new Movie());
        } else
            twoPaneMode = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMovieClicked(@NonNull MoviesAdapter.MovieEventArgs movieArgs) {
        NavigateToDetailMovie(movieArgs.getMovie());
    }

    private void NavigateToDetailMovie(@NonNull Movie movie) {
        if (twoPaneMode) {
            showMovieDetailFragment(movie);
        } else {
            DetailMoviesActivity.StartActivity(this, movie);
        }
    }

    private void showMovieDetailFragment(Movie movie) {
        getSupportFragmentManager().beginTransaction().replace(R.id.movies_detail_container, DetailMoviesFragment.newInstance(movie, true)).commit();
    }
}
