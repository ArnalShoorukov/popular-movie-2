package com.example.arnal.movies;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.arnal.movies.database.MovieDbHelper;
import com.example.arnal.movies.model.Movie;


import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends ListFragment {
    private static final String LOG_TAG = FavoriteFragment.class.getName();

    Cursor cursor;
    SQLiteDatabase db;
    String movieId;
    CursorAdapter adapter;
    static List<Movie> movieList;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            SQLiteOpenHelper openHelper = new MovieDbHelper(getActivity());
            db = openHelper.getReadableDatabase();
            cursor = db.query("movie",
                    new String[]{"_id", "title", "release date", "posterpath"},
                    null, null, null, null, null);

            adapter = new SimpleCursorAdapter(
                    view.getContext(),
                    R.layout.listview_layout,
                    cursor,
                    new String[]{"title"},
                    new int[]{android.R.id.text1},
                    0);

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), R.string.db_unavailable, Toast.LENGTH_LONG);
            toast.show();
        }
        ListView listFavourites = getListView();
        listFavourites.setAdapter(adapter);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        cursor.moveToPosition(position);
        movieId = cursor.getString(1);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(DetailsFragment.MOVIE_ID, movieId);
            intent.putExtra(DetailsFragment.FRAGMENT_TYPE, "FavouriteListFragment");
            startActivity(intent);
    }
}
