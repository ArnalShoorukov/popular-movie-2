package com.example.arnal.movies;

import android.content.ContentValues;
import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arnal.movies.database.MovieContract.MovieEntry;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

/**
 * Created by arnal on 4/8/17.
 */
public class FavouriteDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    ContentResolver mContentResolver;
    private static final int FAVOURITE_LOADER = 13;

    static final String MOVIE_URI = "URI";

    private Uri mUri;

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


    // @BindView(R.id.favorite_icon)
    TextView title;
    ImageView image;
    TextView overview;
    TextView vote;
    TextView releaseDate;


    public FavouriteDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        Bundle args = getArguments();
        if (args != null) {
            mUri = args.getParcelable(FavouriteDetailFragment.MOVIE_URI);
        }
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);
        mContentResolver = getActivity().getContentResolver();

        title = (TextView) rootView.findViewById(R.id.title);
        image = (ImageView) rootView.findViewById(R.id.iv_details);
        releaseDate = (TextView) rootView.findViewById(R.id.releaseDate);
        overview = (TextView) rootView.findViewById(R.id.overviewBody);
        vote = (TextView) rootView.findViewById(R.id.vote);


        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != mUri) {
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    FAVOURITE_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst()) {
            final String movieId = data.getString(COL_MOVIE_ID);
            final String movieTitle = data.getString(COL_MOVIE_TITLE);
            title.setText(movieTitle);

            final String posterPath = data.getString(COL_MOVIE_PATH);
            String myUrl = "https://image.tmdb.org/t/p/w185/";

            Picasso.with(getActivity())
                    .load(myUrl)
                    /*.placeholder(R.drawable.loading_icon) // Displays this image while loading
                    .error(R.drawable.errorstop)    // Displays this image when there is an error*/
                    .into(image);

            final String releasDate = data.getString(COL_MOVIE_DATE);
            releaseDate.setText(releasDate);

            final String voteAverage = data.getString(COL_MOVE_RATING);
            vote.setText(voteAverage + "/10");

            final String moviePlot = data.getString(COL_MOVIE_OVERVIEW);
            overview.setText(moviePlot);


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
