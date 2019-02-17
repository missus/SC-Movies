package com.example.android.missus.scmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.missus.scmovies.adapter.MovieAdapter;
import com.example.android.missus.scmovies.data.Movie;
import com.example.android.missus.scmovies.data.MovieResult;
import com.example.android.missus.scmovies.network.ApiUtils;
import com.example.android.missus.scmovies.network.MovieService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Movie> mMovies;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private MovieService mMovieService;
    private EditText mSearchView;
    private String mQuery;
    public static final String API_KEY = "43a7ea280d085bd0376e108680615c7f";
    public static final String MOVIE_ID = "movie_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchView = findViewById(R.id.query);
        mMovieService = ApiUtils.getMovieService();
        mRecyclerView = findViewById(R.id.movie_grid);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.grid_columns)));
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this, new ArrayList<Movie>(), new MovieAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(MOVIE_ID, mMovieAdapter.getItem(position).id);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mMovieAdapter);
        Button searchButton = findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuery = mSearchView.getText().toString();
                loadMovies();
            }
        });
    }

    private void loadMovies() {
        mMovieService.getMovies(API_KEY, mQuery, 1).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                if (response.isSuccessful()) {
                    mMovies = (response.body().getResults());
                    mMovieAdapter.setMovies(mMovies);
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }
}
