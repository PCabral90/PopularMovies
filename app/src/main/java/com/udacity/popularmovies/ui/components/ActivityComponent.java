package com.udacity.popularmovies.ui.components;

import com.udacity.popularmovies.ApplicationModule;
import com.udacity.popularmovies.data.NetworkModule;
import com.udacity.popularmovies.ui.fragments.ListMoviesFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by pedro on 18-Jun-16.
 */

@Singleton
@Component (modules = {ApplicationModule.class, NetworkModule.class})
public interface ActivityComponent {

    void inject(ListMoviesFragment fragment);
}
