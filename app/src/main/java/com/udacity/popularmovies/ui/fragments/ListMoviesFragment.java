package com.udacity.popularmovies.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.Movie;
import com.udacity.popularmovies.data.api.MovieApi;
import com.udacity.popularmovies.data.response.MoviesResponse;
import com.udacity.popularmovies.ui.adapters.EndlessRecyclerViewScrollListener;
import com.udacity.popularmovies.ui.adapters.MoviesAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pedro on 24-May-16.
 */
public class ListMoviesFragment extends BaseFragment {

    @BindView(R.id.movies_list_recycler)
    RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movies_list;
    }

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                String api_key = getContext().getString(R.string.movies_api_key);

                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", api_key).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();

        final MovieApi movieApi = new Retrofit.Builder()
                .client(client)
                .baseUrl(MovieApi.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi.class);


        moviesAdapter = new MoviesAdapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getMoviesFromApi(movieApi,page);
            }
        });

        getMoviesFromApi(movieApi,1);
    }

    private void getMoviesFromApi(MovieApi movieApi, int page){
        movieApi.getPopularMovies(page).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {
                MoviesResponse movieResponse = response.body();

                List<Movie> movies = new ArrayList<>(response.body().getResults().size());

                for (MoviesResponse.MovieResult movieResult : movieResponse.getResults()) {
                    movies.add(new Movie(movieResult.getPoster_path()));
                }

                int previousCount = moviesAdapter.getItemCount();
                moviesAdapter.addMovies(movies);
                moviesAdapter.notifyItemRangeInserted(previousCount, moviesAdapter.getItemCount());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_movies:
                showSortContextMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortContextMenu() {
        View menuSortView = getActivity().findViewById(R.id.menu_sort_movies);
        PopupMenu popup = new PopupMenu(getContext(), menuSortView);
        popup.getMenuInflater().inflate(R.menu.sort_actions, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_popular:
                        getActivity().setTitle("Popular Movies");
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Not implemented", Snackbar.LENGTH_LONG).show();
                        return true;
                    case R.id.action_top_rated:
                        getActivity().setTitle("Top Rated");
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Not implemented", Snackbar.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        });

        popup.show();
    }

}
