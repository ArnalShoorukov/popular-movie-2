package com.example.arnal.movies.apadter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.arnal.movies.R;
import com.example.arnal.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {
    private Listener listener;
    private Context context;
    private List<Movie> movieList;

    public interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public PosterAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.poster_cardview);
        }
    }

    @Override
    public PosterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterAdapter.ViewHolder holder, final int position) {
        Movie movie = movieList.get(position);
        final ImageView imageView = holder.imageView;
        imageView.setContentDescription(movie.getOriginal_title());

        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster_path())
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_UP:
                        imageView.performClick();
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (movieList.isEmpty() ? 0 : movieList.size());
    }
}
