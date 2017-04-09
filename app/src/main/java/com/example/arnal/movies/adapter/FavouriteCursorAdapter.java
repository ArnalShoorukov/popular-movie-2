package com.example.arnal.movies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.arnal.movies.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;

import com.example.arnal.movies.database.MovieContract.MovieEntry;

/**
 * Created by arnal on 4/8/17.
 */

public class FavouriteCursorAdapter extends CursorAdapter {
    public FavouriteCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.movie_cardview, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView posterImage = (ImageView) view.findViewById(R.id.poster_cardview);

        int movieIdColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID);
        int posterColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH);

        String movieId = cursor.getString(movieIdColumnIndex);
        String posterPath = cursor.getString(posterColumnIndex);


        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + posterPath)
                .into(posterImage);


    }
}
