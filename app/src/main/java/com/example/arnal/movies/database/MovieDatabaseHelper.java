package com.example.arnal.movies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "movies";
    private static final int DB_VERSION = 1;

    public MovieDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MOVIE(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MOVIE_ID INTEGER UNIQUE," +
                "ORIGINAL_TITLE TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
