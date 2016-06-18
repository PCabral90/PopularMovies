package com.udacity.popularmovies.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.udacity.popularmovies.PopularMoviesApp;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.model.Movie;
import com.udacity.popularmovies.data.api.MovieApi;
import com.udacity.popularmovies.data.response.MoviesResponse;
import com.udacity.popularmovies.ui.adapters.EndlessRecyclerViewScrollListener;
import com.udacity.popularmovies.ui.adapters.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by pedro on 24-May-16.
 */
public class ListMoviesFragment extends BaseFragment implements Callback<MoviesResponse> {

    @Inject
    Retrofit retrofit;

    @BindView(R.id.movies_list_recycler)
    RecyclerView recyclerView;

    private MoviesAdapter moviesAdapter;
    private MovieApi movieApi;

    private SortMoviesBy sortBy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        PopularMoviesApp.getPopularMovieApp(getContext()).getActivityComponent().inject(this);

        movieApi = retrofit.create(MovieApi.class);
        sortBy = SortMoviesBy.MostPopular;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moviesAdapter = new MoviesAdapter();
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getMoviesFromApi(page, sortBy);
            }
        });

        getMoviesFromApi(1, sortBy);
    }

    private void getMoviesFromApi(int page, SortMoviesBy sortby) {
        if (sortby == SortMoviesBy.MostPopular)
            movieApi.getPopularMovies(page).enqueue(this);
        else
            movieApi.getTopRated(page).enqueue(this);
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
                        sortMovies("Popular Movie", SortMoviesBy.MostPopular);
                        return true;
                    case R.id.action_top_rated:
                        sortMovies("Top Rated", SortMoviesBy.TopRated);
                        return true;
                }
                return false;
            }
        });

        popup.show();
    }

    private void sortMovies(String title, SortMoviesBy sortBy) {
        this.sortBy = sortBy;
        getActivity().setTitle(title);
        moviesAdapter.clear();
        getMoviesFromApi(1, sortBy);
    }


    @Override
    public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {
        MoviesResponse movieResponse = response.body();

        List<Movie> movies = new ArrayList<>(response.body().getResults().size());

        for (MoviesResponse.MovieResult movieResult : movieResponse.getResults()) {
            movies.add(new Movie(
                    movieResult.getPoster_path(),
                    movieResult.getBackdrop_path(),
                    movieResult.getOriginal_title(),
                    movieResult.getRelease_date(),
                    movieResult.getVote_average(),
                    movieResult.getOverview()
            ));
        }

        moviesAdapter.addMovies(movies);
    }

    @Override
    public void onFailure(Call<MoviesResponse> call, Throwable t) {
        Toast.makeText(getContext(), "Error while loading movies", Toast.LENGTH_LONG).show();
    }

    public enum SortMoviesBy {
        MostPopular,
        TopRated
    }
}
