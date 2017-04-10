package com.example.arnal.movies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.arnal.movies.adapter.PosterAdapter;
import com.example.arnal.movies.model.MessageEvent;
import com.example.arnal.movies.model.Movie;
import com.example.arnal.movies.model.MoviesAPI;
import com.example.arnal.movies.model.QueryResult;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatedFragment extends Fragment {

    static List<Movie> movieList;
    private PosterAdapter adapter;


    public RatedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getRatedMovies();

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_rated, container,false);
        adapter = new PosterAdapter(getActivity(), movieList);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        adapter.setListener(new PosterAdapter.Listener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailsFragment.POSITION, position);
                intent.putExtra(DetailsFragment.FRAGMENT_TYPE, "RatedFragment");

                getActivity().startActivity(intent);
            }
        });
        return recyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getRatedMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesAPI api = retrofit.create(MoviesAPI.class);
        movieList = new ArrayList<>();
        api.getFeedTopRated(MainActivity.API_KEY).enqueue(new Callback<QueryResult>() {
            @Override
            public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                QueryResult result = response.body();
                movieList = result.getResults();
                adapter.setMovieList(movieList);

                EventBus.getDefault().post(new MessageEvent(0, "RatedFragment", 0));
            }

            @Override
            public void onFailure(Call<QueryResult> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(), R.string.network_unavailable, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
