package com.example.arnal.movies;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.arnal.movies.adapter.FavouriteCursorAdapter;
import com.example.arnal.movies.database.MovieContract.MovieEntry;
import com.example.arnal.movies.model.Movie;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = FavoriteFragment.class.getName();



    // Identifier for the Loader
    private static final int FAVOURITE_LOADER = 0;
    FavouriteCursorAdapter mCursorAdapter;
    private static final String[] FAVOURITE_COLUMNS = {
            MovieEntry._ID,
            MovieEntry.COLUMN_MOVIE_ID,
            MovieEntry.COLUMN_TITLE,
            MovieEntry.COLUMN_POSTER_PATH,
            MovieEntry.COLUMN_RELEASE_DATE,
            MovieEntry.COLUMN_VOTE,
            MovieEntry.COLUMN_OVERVIEW,
    };

    // These indices are tied to FAVOURITE_COLUMNS.
    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_MOVIE_TITLE = 2;
    public static final int COL_MOVIE_PATH = 3;
    public static final int COL_MOVIE_DATE = 4;
    public static final int COL_MOVE_RATING = 5;
    public static final int COL_MOVIE_OVERVIEW = 6;
     GridView favouriteGridView;


    private int mPosition = GridView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";


    /**
     * A callback interface that allows the fragment to pass data on to the activity
     */
    public interface Callback {
        public void onFavouriteSelected(Uri contentUri);
    }

    public FavoriteFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kick off the loader
        getActivity().getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, saves the currently selected list item
       /* if (mPosition != GridView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }*/
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);


        favouriteGridView = (GridView)rootView.findViewById(R.id.movie_activity_grid_view);


        mCursorAdapter = new FavouriteCursorAdapter(getContext(), null);

        favouriteGridView.setAdapter(mCursorAdapter);


        // Click on a movie to show its details
        favouriteGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                if (cursor != null) {
                    String columnId = cursor.getString(cursor.getColumnIndex(MovieEntry._ID));

                    Log.i(LOG_TAG, "Colunm Id value" + columnId);

                }
                mPosition = i;



                Toast.makeText(getActivity().getApplicationContext(), "yes click -  Favorite List", Toast.LENGTH_SHORT).show();

            }
        });

        // If there's instance state, mine it for useful info
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MovieEntry._ID,
                MovieEntry.COLUMN_MOVIE_ID,
                MovieEntry.COLUMN_POSTER_PATH};

        return new CursorLoader(getContext(),
                MovieEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
       /* if (cursor == null || !cursor.moveToFirst()) {
            mEmptyStateTextView.setText(R.string.empty_favourite_gridview_message);
        }*/
        mCursorAdapter.swapCursor(cursor);

        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            favouriteGridView.smoothScrollToPosition(mPosition);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
