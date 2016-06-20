package com.udacity.popularmovies.ui.components;

import com.udacity.popularmovies.PopularMoviesAppModule;
import com.udacity.popularmovies.data.MovieApiModule;
import com.udacity.popularmovies.ui.fragments.ListMoviesFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by pedro on 18-Jun-16.
 */


@Singleton
@Component(modules = {PopularMoviesAppModule.class, MovieApiModule.class, ListMovieModule.class})
public interface ListMovieComponent {

    void inject(ListMoviesFragment fragment);

}
