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

import static com.example.arnal.movies.DetailsFragment.setFavouriteImageText;

/**
 * Created by arnal on 4/8/17.
 */
public class FavouriteDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    ContentResolver mContentResolver;
    private static final int FAVOURITE_LOADER = 13;

    ImageView mFavouriteIcon;
    TextView mFavouriteTextView;
    LinearLayout favouriteView;

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
        mFavouriteIcon = (ImageView) rootView.findViewById(R.id.favorite_icon);
        mFavouriteTextView = (TextView) rootView.findViewById(R.id.favourite_text_view);
        favouriteView = (LinearLayout) rootView.findViewById(R.id.favourite_view);

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
                    .load(myUrl + posterPath)
                    .placeholder(R.drawable.loading_icon) // Displays this image while loading
                    .error(R.drawable.ic_stat_name)    // Displays this image when there is an error
                    .into(image);


            final String releasDate = data.getString(COL_MOVIE_DATE);
            releaseDate.setText(releasDate);

            final String voteAverage = data.getString(COL_MOVE_RATING);
            vote.setText(voteAverage + "/10");

            final String moviePlot = data.getString(COL_MOVIE_OVERVIEW);
            overview.setText(moviePlot);

            mFavouriteIcon.setImageResource(R.drawable.ic_fav);
            mFavouriteTextView.setText(R.string.favourited);
            favouriteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String favouriteText = mFavouriteTextView.getText().toString();

                    //Check whether the movie is favourited
                    if (favouriteText.equals(getString(R.string.favourited))) {
                        // Unmark as favourite and delete it from the database

                        mContentResolver.delete(
                                mUri,
                                null,
                                null
                        );

                        // Update favourite icon and text
                        setFavouriteImageText(false, mFavouriteIcon, mFavouriteTextView);
                    } else {
                        // Add as favourite and insert it into the database
                        ContentValues values = new ContentValues();
                        values.put(MovieEntry.COLUMN_MOVIE_ID, movieId);
                        values.put(MovieEntry.COLUMN_TITLE, movieTitle);
                        values.put(MovieEntry.COLUMN_POSTER_PATH, posterPath);
                        values.put(MovieEntry.COLUMN_RELEASE_DATE, releasDate);
                        values.put(MovieEntry.COLUMN_VOTE, voteAverage);
                        values.put(MovieEntry.COLUMN_OVERVIEW, moviePlot);

                        Uri newUri = mContentResolver.insert(MovieEntry.CONTENT_URI, values);

                        // Update favourite icon and text
                        setFavouriteImageText(true, mFavouriteIcon, mFavouriteTextView);
                    }
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
