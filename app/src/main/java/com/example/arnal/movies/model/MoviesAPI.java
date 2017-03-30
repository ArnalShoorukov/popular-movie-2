package com.example.arnal.movies.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesAPI {

    @GET("movie/top_rated")
    Call<QueryResult> getFeedTopRated(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<QueryResult> getFeedPopular(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovies(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailersResult> getTrailers(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewResult> getReview(@Path("id") String id, @Query("api_key") String apiKey);

}
