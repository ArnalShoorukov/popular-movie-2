package com.example.arnal.movies;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arnal.movies.model.Movie;
import com.example.arnal.movies.model.MoviesAPI;
import com.example.arnal.movies.model.Review;
import com.example.arnal.movies.model.ReviewResult;
import com.example.arnal.movies.model.Trailer;
import com.example.arnal.movies.model.TrailersResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.arnal.movies.R.id.scrollView;
import static com.example.arnal.movies.R.id.trailerText;

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
    private Trailer trailer;
    private Context context;
    int id;
    MoviesAPI api;
    ArrayList<Trailer> movieTrailers = new ArrayList<>();
    public static ArrayList<Review> reviewList;
    String movieTitle;
    int position;
    String fragmentType;
    Bundle bundle;
    String movieId;
    Retrofit retrofit;



    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.movie_detail_fragment, container, false);
        position = (int) getActivity().getIntent().getExtras().get(POSITION);
        fragmentType = getActivity().getIntent().getStringExtra(FRAGMENT_TYPE);
        if (fragmentType.equals("PopularFragment")) {
            movie = PopularFragment.movieList.get(position);



        } else if (fragmentType.equals("RatedFragment")) {
            movie = RatedFragment.movieList.get(position);


            Log.i(LOG_TAG, "Show me fragment type: " + fragmentType);

        }    setData(scrollView);

        // ButterKnife.bind(this, scrollView);


       movieId = movie.getId();
        Log.i(LOG_TAG, "Show me fragment type: " + movieId);

   // getTrailer();


        return scrollView;
    }



   /* private void getTrailer() {

        retrofitCall();
        trailerList = new ArrayList<>();
        api.getTrailers(movieId, API_KEY).enqueue(new Callback<TrailersResult>() {
            @Override
            public void onResponse(Call<TrailersResult> call, Response<TrailersResult> response) {
                TrailersResult trailersResult = response.body();
                trailerList = trailersResult.getResults();
                String trailerKey = trailerList.iterator().next().getKey();
                Log.i(LOG_TAG, "Show me fragment type: " + trailerList);
                String videoPath = "https://www.youtube.com/watch?v=" + trailerKey;
                String trailer  = trailerList.iterator().next().getName();

                Log.i(LOG_TAG, "Show me trailer name: " + trailer);



                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoPath));
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<TrailersResult> call, Throwable t) {
                Toast toast = Toast.makeText(context, R.string.error, Toast.LENGTH_LONG);
                toast.show();
            }
        });
*/
/*
    }
*/

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
        String dateMovie = new String(movie.getId());
        date.setText(dateMovie);

        TextView overview = (TextView) scrollView.findViewById(R.id.overviewBody);
        String overviewMovie = new String(movie.getOverview());
        overview.setText(overviewMovie);




    }





    public void retrofitCall() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MoviesAPI.class);
    }


}