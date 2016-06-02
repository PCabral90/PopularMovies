package com.udacity.popularmovies.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.Movie;
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

    private List<Movie> movies = new ArrayList<>(20);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movies_list;
    }

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        setDummyString();

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MoviesAdapter(movies));
    }

    private void setDummyString() {
        movies.add(new Movie("nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"));
        movies.add(new Movie("inVq3FRqcYIRl2la8iZikYYxFNR.jpg"));
        movies.add(new Movie("zSouWWrySXshPCT4t3UKCQGayyo.jpg"));
        movies.add(new Movie("5N20rQURev5CNDcMjHVUZhpoCNC.jpg"));
        movies.add(new Movie("sM33SANp9z6rXW8Itn7NnG1GOEs.jpg"));
        movies.add(new Movie("uPqAW07bGoljf3cmT5gecdOvVol.jpg"));
        movies.add(new Movie("s7OVVDszWUw79clca0durAIa6mw.jpg"));
        movies.add(new Movie("wx9vNunt4Q9iUbmwWBtzUM5g0SU.jpg"));
        movies.add(new Movie("yHhBfL2msdFWwLpjGSgPJpVmxQN.jpg"));
        movies.add(new Movie("vdK1f9kpY5QEwrAiXs9R7PlerNC.jpg"));
    }
}
