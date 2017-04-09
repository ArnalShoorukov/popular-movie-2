package com.example.arnal.movies.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by arnal on 4/6/17.
 */

public class MovieContract {

    public static final String AUTHORITY = "com.example.arnal.movies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY );

    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
       // public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "movie" ;

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_VOTE = "voteAverage";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER_PATH = "posterPath";

        public static Uri buildMovieUriWithId(String _ID){
            return CONTENT_URI.buildUpon().appendPath(_ID)
                    .build();
            }


    }
}
