package com.example.arnal.movies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.example.arnal.movies.database.MovieContract.MovieEntry.TABLE_NAME;

/**
 * Created by arnal on 4/6/17.
 */

public class MovieContentProvider extends ContentProvider {

    public static final int MOVIE = 100;
    public static final int MOVIE_MITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE, MOVIE);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE + "/#", MOVIE_MITH_ID);

        return uriMatcher;
    }


    private MovieDbHelper movieDbHelper;

    @Override
    public boolean onCreate() {

        Context context = getContext();
        movieDbHelper = new MovieDbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match){
            case MOVIE:
                //Inserting values into movie table
                long id = db.insert(TABLE_NAME, null, values);

                if (id > 0 ){

                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
                break;
            //Default case thorows an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
