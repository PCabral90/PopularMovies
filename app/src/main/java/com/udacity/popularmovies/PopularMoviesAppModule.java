package com.udacity.popularmovies;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pedro on 17-Jun-16.
 */
@Module
public class PopularMoviesAppModule {


    private Application app;

    public PopularMoviesAppModule(Application app) {
        this.app = app;
    }

    @Provides
    public Context providesContext() {
        return app;
    }
}
