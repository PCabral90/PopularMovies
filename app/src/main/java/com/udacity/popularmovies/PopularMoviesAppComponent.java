package com.udacity.popularmovies;

import com.udacity.popularmovies.data.MoviesApiModule;
import com.udacity.popularmovies.ui.fragments.ListMoviesFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by pedro on 20-Jun-16.
 */

@Singleton
@Component (modules = {PopularMoviesAppModule.class, MoviesApiModule.class})
public interface PopularMoviesAppComponent {

    void inject(ListMoviesFragment listMoviesFragment);
}
