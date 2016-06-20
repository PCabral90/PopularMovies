package com.udacity.popularmovies;

import android.app.Application;
import android.content.Context;

import com.udacity.popularmovies.data.MoviesApiModule;
import com.udacity.popularmovies.data.api.MovieApi;

/**
 * Created by pedro on 23-May-16.
 */
public class PopularMoviesApp extends Application {

    private PopularMoviesAppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerPopularMoviesAppComponent.builder()
                .popularMoviesAppModule(new PopularMoviesAppModule(this))
                .moviesApiModule(new MoviesApiModule(MovieApi.ENDPOINT))
                .build();
    }

    public static PopularMoviesApp getPopularMovieApp(Context ctx) {
        return (PopularMoviesApp) ctx.getApplicationContext();
    }

    public PopularMoviesAppComponent getPopularMoviesComponent() {
        return appComponent;
    }
}
