package com.udacity.popularmovies.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.model.Movie;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pedro on 24-May-16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {


    public class MovieEventArgs {

        private final Movie movie;

        public MovieEventArgs(@NonNull Movie movie) {

            this.movie = movie;
        }

        public Movie getMovie() {
            return movie;
        }
    }

    protected final class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_poster)
        ImageView poster;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(@NonNull final Movie movie) {
            poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MovieEventArgs(movie));
                }
            });

            Glide.with(context)
                    .load("http://image.tmdb.org/t/p/w342/" + movie.getPoster())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(poster);
        }
    }

    private List<Movie> movies = new ArrayList<>();
    private Context context;

    public MoviesAdapter() {}

    public MoviesAdapter(@NonNull List<Movie> movies) {
        addMovies(movies);
    }

    public void addMovies(@NonNull List<Movie> movies) {
        int previousCount = getItemCount();
        this.movies.addAll(movies);
        notifyItemRangeInserted(previousCount, getItemCount());
    }

    public void clear(){
        int count = getItemCount();
        movies.clear();
        notifyItemRangeRemoved(0, count);
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
}
