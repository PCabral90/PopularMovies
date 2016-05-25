package com.udacity.popularmovies.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.ui.adapters.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by pedro on 24-May-16.
 */
public class ListMoviesFragment extends BaseFragment {

    @BindView(R.id.movies_list_recycler)
    protected RecyclerView recyclerView;

    private List<String> movies = new ArrayList<>(20);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movies_list;
    }

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        setDummyString();

        int numberCol = 2;
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MoviesAdapter(movies));
    }

    private void setDummyString() {
        movies.add("A");
        movies.add("B");
        movies.add("C");
        movies.add("D");
        movies.add("E");
        movies.add("F");
        movies.add("G");
    }
}
