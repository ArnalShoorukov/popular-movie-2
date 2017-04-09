package com.example.arnal.movies;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FavorDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_details);

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putParcelable(FavouriteDetailFragment.MOVIE_URI, getIntent().getData());

            FavouriteDetailFragment fragment = new FavouriteDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().add(R.id.activity_favor_details, fragment).commit();
        }
    }
}