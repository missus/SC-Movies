package com.example.android.missus.scmovies.network;

import com.example.android.missus.scmovies.data.MovieDetails;
import com.example.android.missus.scmovies.data.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("search/movie")
    Call<MovieResult> getMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int pageIndex);

    @GET("movie/{id}")
    Call<MovieDetails> getMovieDetails(@Path("id") int movieId, @Query("api_key") String apiKey);
}
