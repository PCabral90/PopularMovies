package com.udacity.popularmovies.data;

import android.content.Context;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.api.MovieApi;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pedro on 17-Jun-16.
 */
@Module
public class NetworkModule {

    private String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient (final Context context){
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                String api_key = context.getString(R.string.movies_api_key);

                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", api_key).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Singleton
    @Provides
    public GsonConverterFactory providesGsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    public RxJavaCallAdapterFactory providesRxJavaCallAdapterFactory(){
        return RxJavaCallAdapterFactory.create();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit (OkHttpClient okHttpClient, GsonConverterFactory gson, RxJavaCallAdapterFactory rxJava){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(rxJava)
                .addConverterFactory(gson)
                .build();
    }
}
