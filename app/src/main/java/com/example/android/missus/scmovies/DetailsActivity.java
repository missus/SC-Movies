package com.example.android.missus.scmovies;

import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.missus.scmovies.data.MovieDetails;
import com.example.android.missus.scmovies.network.ApiUtils;
import com.example.android.missus.scmovies.network.MovieService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {


    private int mMovieId;
    private MovieDetails mMovieDetails;
    private MovieService mMovieService;
    private TextView mTitle;
    private TextView mBudget;
    private TextView mRating;
    private TextView mReleaseDate;
    private TextView mOverview;
    private ImageView mPosterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        mMovieService = ApiUtils.getMovieService();
        mMovieId = getIntent().getIntExtra(MainActivity.MOVIE_ID, 0);
        mPosterImage = findViewById(R.id.poster);
        mTitle = findViewById(R.id.title);
        mBudget = findViewById(R.id.budget);
        mRating = findViewById(R.id.rating);
        mReleaseDate = findViewById(R.id.release_date);
        mOverview = findViewById(R.id.overview);
        loadMovieDetails();
    }

    private void setDetails() {
        mTitle.setText(mMovieDetails.getTitle());
        mBudget.setText(mMovieDetails.getBudget() + "$");
        mRating.setText(mMovieDetails.getVoteAverage() + "/10");
        mReleaseDate.setText(mMovieDetails.getReleaseDate());
        mOverview.setText(mMovieDetails.getOverview());
        Picasso.with(this).load(mMovieDetails.getPosterUrl()).error(R.mipmap.ic_launcher).into(mPosterImage);
    }

    private void loadMovieDetails() {

        mMovieService.getMovieDetails(mMovieId, MainActivity.API_KEY).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                if (response.isSuccessful()) {
                    mMovieDetails = response.body();
                    setDetails();
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.d("DetailsActivity", "error loading from API");
            }
        });
    }
}
