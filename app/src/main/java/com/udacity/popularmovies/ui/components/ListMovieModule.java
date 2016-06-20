package com.udacity.popularmovies.ui.components;

import com.udacity.popularmovies.data.api.MovieApi;
import com.udacity.popularmovies.ui.fragments.ListMovieView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by pedro on 20-Jun-16.
 */

@Module
public class ListMovieModule {

    private ListMovieView view;

    public ListMovieModule(ListMovieView view) {

        this.view = view;
    }

    @Provides
    public ListMovieView providesListMovieView() {
        return view;
    }


}
