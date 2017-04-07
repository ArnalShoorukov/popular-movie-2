package com.example.arnal.movies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.arnal.movies.database.MovieContract.MovieEntry;

/**
 * Created by arnal on 4/6/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "movies.db";
    private static final int DB_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {



        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID                   + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_TITLE          +  " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE   + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_VOTE           + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW       + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POSTER_PATH    + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);

    }
}
