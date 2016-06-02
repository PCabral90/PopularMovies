package com.udacity.popularmovies.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.ui.fragments.ListMoviesFragment;

import butterknife.BindView;

public class ListMoviesActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.movies_detail_container)
    protected View detailContainer;

    private boolean twoPaneMode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movies_list;
    }

    @Override
    protected void initializeLayout(Bundle savedInstanceState) {

        if (detailContainer != null) {
            twoPaneMode = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movies_detail_container, new ListMoviesFragment())
                        .commit();
            }
        } else
            twoPaneMode = false;
    }
}
