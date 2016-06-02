package com.udacity.popularmovies.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pedro on 24-May-16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movies;
    private Context context;

    public MoviesAdapter(@NonNull List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View rootView = LayoutInflater.from(this.context).inflate(R.layout.movies_list_content, parent, false);
        return new MoviesViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    protected final class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_poster)
        ImageView poster;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(@NonNull final Movie movie) {
           Glide.with(context)
                   .load("http://image.tmdb.org/t/p/w342/" + movie.getPoster())
                   .placeholder(R.mipmap.ic_launcher)
                   .into(poster);
        }
    }
}
