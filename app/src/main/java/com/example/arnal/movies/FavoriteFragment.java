package com.example.arnal.movies;


import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
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
    private static final int FAVOURITE_LOADER = 3;
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
    TextView mEmptyStateTextView;

    private int mPosition = GridView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";


    /**
     * A callback interface that allows the fragment to pass data on to the activity
     */
    public interface Callback {
        void onFavouriteSelected(Uri contentUri);
    }

    public FavoriteFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kick off the loader
        Log.i(LOG_TAG, "Test" + FAVOURITE_LOADER);
        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);


        favouriteGridView = (GridView)rootView.findViewById(R.id.movie_activity_grid_view);
        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_list_view);
        Cursor cursor = null;

        mCursorAdapter = new FavouriteCursorAdapter(getContext(), cursor);

        favouriteGridView.setAdapter(mCursorAdapter);
        favouriteGridView.setEmptyView(mEmptyStateTextView);

        mCursorAdapter.setListener(new FavouriteCursorAdapter.Listener() {
            @Override
            public void onClick(int position) {

                Cursor cursor = (Cursor) mCursorAdapter.getItem(position);
                if (cursor != null) {
                    String columnId = cursor.
                            getString(cursor.getColumnIndex(MovieEntry._ID));
                    Uri contentUri = MovieEntry.buildMovieUriWithId(columnId);
                    Intent intent = new Intent(getContext(), FavorDetailsActivity.class);
                    intent.setData(contentUri);
                    startActivity(intent);
                }
                mPosition = position;
            }
        });

        // If there's instance state, mine it for useful info
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // re-queries for all tasks
        getActivity().getSupportLoaderManager().restartLoader(FAVOURITE_LOADER, null, this);
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
        if (cursor == null || !cursor.moveToFirst()) {
            mEmptyStateTextView.setText(R.string.empty_favourite_gridview_message);
        }

        Log.i(LOG_TAG, "Test Cursor before swap " + cursor);
        mCursorAdapter.swapCursor(cursor);
        Log.i(LOG_TAG, "Test Cursor Adapter" + mCursorAdapter);
        Log.i(LOG_TAG, "Test Cursor Adapter Swap" + mCursorAdapter.swapCursor(cursor));

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
