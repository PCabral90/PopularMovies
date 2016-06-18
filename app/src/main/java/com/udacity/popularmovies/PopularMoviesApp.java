package com.udacity.popularmovies;

import android.app.Application;
import android.content.Context;

import com.udacity.popularmovies.data.NetworkModule;
import com.udacity.popularmovies.data.api.MovieApi;
import com.udacity.popularmovies.ui.components.ActivityComponent;
import com.udacity.popularmovies.ui.components.DaggerActivityComponent;

/**
 * Created by pedro on 23-May-16.
 */
public class PopularMoviesApp extends Application {

    private ActivityComponent activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        activityComponent = DaggerActivityComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(MovieApi.ENDPOINT))
                .build();
    }

    public static PopularMoviesApp getPopularMovieApp(Context ctx) {
        return (PopularMoviesApp) ctx.getApplicationContext();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
