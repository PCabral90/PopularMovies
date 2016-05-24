package com.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.popularmovies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pedro on 24-May-16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<String> movies;
    private Context context;

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        public TextView text;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public MoviesAdapter(List<String> movies) {
        this.movies = movies;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        View rootView = LayoutInflater.from(context).inflate(R.layout.movies_list_content, parent, false);

        return new MoviesViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.text.setText(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


}
