package com.udacity.popularmovies.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.udacity.popularmovies.ui.adapters.EndlessRecyclerViewScrollListener;
import com.udacity.popularmovies.ui.adapters.MoviesAdapter;
import com.udacity.popularmovies.ui.presenters.ListMoviePresenterImpl;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Retrofit;

/**
 * Created by pedro on 24-May-16.
 */

public class ListMoviesFragment extends BaseFragment implements ListMovieView {

    private static final String SORT_BY_KEY = "sort_by_key";

    @Inject
    Retrofit retrofit;

    @Inject
    ListMoviePresenterImpl presenter;

    @BindView(R.id.movies_list_recycler)
    RecyclerView recyclerView;

    private MoviesAdapter moviesAdapter;
    private SortMoviesBy sortBy;
    private SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        PopularMoviesApp.getPopularMovieApp(getContext()).getPopularMoviesComponent().inject(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sortBy = SortMoviesBy.getSortMoviesByString(preferences.getString(SORT_BY_KEY, SortMoviesBy.MostPopular.toString()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.attachView(this);

        moviesAdapter = new MoviesAdapter();
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMovies(page, sortBy);
            }
        });

        loadMovies(1, sortBy);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dettachView();
    }

    @Override
    public void onMoviesLoaded(List<Movie> movies) {
        moviesAdapter.addMovies(movies);
    }

    @Override
    public void onMoviesLoadedError(Throwable t) {
        Toast.makeText(getContext(), R.string.error_loading_movies, Toast.LENGTH_LONG).show();
    }

    private void loadMovies(int page, SortMoviesBy sortBy) {
        switch (sortBy) {
            case TopRated:
                getActivity().setTitle(R.string.sort_action_top_rated);
                presenter.loadTopRatedMoviesByPage(page);
                break;
            case MostPopular:
            default:
                getActivity().setTitle(R.string.sort_action_popular_movies);
                presenter.loadPopularMoviesByPage(page);
                break;
        }
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
                        sortMovies( SortMoviesBy.MostPopular);
                        return true;
                    case R.id.action_top_rated:
                        sortMovies(SortMoviesBy.TopRated);
                        return true;
                }
                return false;
            }
        });

        popup.show();
    }

    private void sortMovies(SortMoviesBy sortBy) {
        this.sortBy = sortBy;

        preferences.edit().putString(SORT_BY_KEY, sortBy.toString()).apply();
        moviesAdapter.clear();

        loadMovies(1, sortBy);
    }


    public enum SortMoviesBy {
        MostPopular,
        TopRated;

        public static SortMoviesBy getSortMoviesByString(String sortBy) {
            return valueOf(sortBy);
        }
    }
}
