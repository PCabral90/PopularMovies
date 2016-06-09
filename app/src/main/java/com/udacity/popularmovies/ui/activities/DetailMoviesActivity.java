package com.udacity.popularmovies.ui.activities;

import android.os.Bundle;

import com.udacity.popularmovies.R;

/**
 * Created by pedro on 24-May-16.
 */
public class DetailMoviesActivity extends BaseActivity {

    public static final String EXTRA_MOVIE = "com.udacity.popularmovies.extras.EXTRA_MOVIE";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movies_detail;
    }

    @Override
    protected void initializeLayout(Bundle savedInstanceState) {

    }
}
