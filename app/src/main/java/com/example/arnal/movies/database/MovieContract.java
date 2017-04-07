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

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "movie" ;

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release date";
        public static final String COLUMN_VOTE = "vote average";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER_PATH = "poster path";

    }
}
