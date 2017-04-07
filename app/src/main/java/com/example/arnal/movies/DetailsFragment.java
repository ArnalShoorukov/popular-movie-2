package com.example.arnal.movies;


import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arnal.movies.adapter.ReviewsAdapter;
import com.example.arnal.movies.adapter.TrailerAdapter;
import com.example.arnal.movies.database.MovieContract;
import com.example.arnal.movies.model.Movie;
import com.example.arnal.movies.model.MoviesAPI;
import com.example.arnal.movies.model.Review;
import com.example.arnal.movies.model.ReviewResult;
import com.example.arnal.movies.model.Trailer;
import com.example.arnal.movies.model.TrailersResult;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    private static final String LOG_TAG = DetailsFragment.class.getName();

    private static final String ENDPOINT = "http://api.themoviedb.org/3/";
    private static final String API_KEY = BuildConfig.MOVIES_TMDB_API_KEY;
    static final String POSITION = "position";
    static final String FRAGMENT_TYPE = "fragment";
    static final String MOVIE_ID = "movieId";
    private Movie movie;
    MoviesAPI api;
    public static ArrayList<Review> reviewList;
    public static ArrayList<Trailer> trailerList;
    String movieTitle;
    int position;
    String fragmentType;
    String movieId;
    Retrofit retrofit;
    private ReviewsAdapter adapter;
    private TrailerAdapter trailerAdapter;
    private Context context;
    String id;


    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.movie_detail_fragment, container, false);
        fragmentType = getActivity().getIntent().getStringExtra(FRAGMENT_TYPE);

        if (fragmentType != null) {
            switch (fragmentType) {
                case "PopularFragment":
                    position = (int) getActivity().getIntent().getExtras().get(POSITION);
                    movie = PopularFragment.movieList.get(position);
                    id = movie.getId();
                    setData(scrollView);
                    break;
                case "RatedFragment":
                    position = (int) getActivity().getIntent().getExtras().get(POSITION);
                    movie = RatedFragment.movieList.get(position);
                    id = movie.getId();
                    setData(scrollView);
                    break;

                case "FavouriteListFragment":
                    movieId = getActivity().getIntent().getExtras().getString(MOVIE_ID);
                    id = movieId;
                    retrofitCall();
                    api.getMovies(movieId, API_KEY).enqueue(new Callback<Movie>() {
                        @Override
                        public void onResponse(Call<Movie> call, Response<Movie> response) {
                            movie = response.body();
                            setData(scrollView);
                        }

                        @Override
                        public void onFailure(Call<Movie> call, Throwable t) {
                            Toast toast = Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                    break;
            }
        }
        FloatingActionButton fab = (FloatingActionButton) scrollView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to Favorite", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addFavourite();
            }
        });

        RecyclerView recyclerView = (RecyclerView) scrollView.findViewById(R.id.recyclerView_review);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ReviewsAdapter(getActivity(), reviewList);
        recyclerView.setAdapter(adapter);

        RecyclerView recyclerViewTrailer = (RecyclerView) scrollView.findViewById(R.id.recyclerview_trailers);
        LinearLayoutManager layoutManager2 =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTrailer.setLayoutManager(layoutManager2);
        recyclerViewTrailer.setHasFixedSize(true);
        trailerAdapter = new TrailerAdapter(getActivity(), trailerList);
        recyclerViewTrailer.setAdapter(trailerAdapter);

        getTrailer();
        getReviews();

        return scrollView;
    }

    private void getTrailer() {

        retrofitCall();
        trailerList = new ArrayList<>();
        api.getTrailers(id, API_KEY).enqueue(new Callback<TrailersResult>() {
            @Override
            public void onResponse(Call<TrailersResult> call, Response<TrailersResult> response) {
                TrailersResult trailersResult = response.body();
                trailerList = (ArrayList<Trailer>) trailersResult.getResults();
                trailerAdapter.setTrailerList(trailerList);
            }

            @Override
            public void onFailure(Call<TrailersResult> call, Throwable t) {
                Toast toast = Toast.makeText(context, R.string.error, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void setData(ScrollView scrollView) {
        TextView title = (TextView) scrollView.findViewById(R.id.title);
        movieTitle = movie.getOriginal_title();
        title.setText(movieTitle);

        ImageView image = (ImageView) scrollView.findViewById(R.id.iv_details);
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster_path())
                .into(image);

        TextView date = (TextView) scrollView.findViewById(R.id.releaseDate);
        String dateMovie = new String(movie.getRelease_date());
        date.setText(dateMovie);

        TextView overview = (TextView) scrollView.findViewById(R.id.overviewBody);
        String overviewMovie = new String(movie.getOverview());
        overview.setText(overviewMovie);

        Log.i(LOG_TAG, "Show me vote: " + movie.getVote_average());

        TextView rate = (TextView) scrollView.findViewById(R.id.vote);
        String rating = new String(movie.getVote_average());
        rate.setText(rating + "/10");
    }

    public void retrofitCall() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MoviesAPI.class);
    }

    public void getReviews() {

        retrofitCall();
        reviewList = new ArrayList<>();
        api.getReview(id, API_KEY).enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                ReviewResult reviewResult = response.body();
                reviewList = (ArrayList<Review>) reviewResult.getResults();
                adapter.setReviewList(reviewList);

            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void addFavourite() {
        /*SQLiteOpenHelper dbHelper = new MovieDbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MOVIE_ID", movie.getId());
        contentValues.put("ORIGINAL_TITLE", movie.getOriginal_title());
        contentValues.put("POSTER_PATH", movie.getPoster_path());
        db.insert("MOVIE", null, contentValues);
        db.insertWithOnConflict("MOVIE", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);*/


      //New Content values object
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getOriginal_title());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getRelease_date());
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE, movie.getVote_average());
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPoster_path());




        Uri uri = getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);


        /*if(uri != null){
            Toast.makeText(getActivity().getApplicationContext().getBaseContext(), uri.toString(),Toast.LENGTH_LONG).show();
        }*/
    }
}






