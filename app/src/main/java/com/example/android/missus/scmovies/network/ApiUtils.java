package com.example.android.missus.scmovies.network;

public class ApiUtils {

    private static final String BASE_URL = "http://api.themoviedb.org/3/";

    public static MovieService getMovieService() {
        return RetrofitClient.getClient(BASE_URL).create(MovieService.class);
    }}
