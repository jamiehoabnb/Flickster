package com.codepath.flickster.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        String imageBaseURL = getIntent().getStringExtra("imageBaseURL");
        final String POSTER_IMAGE_SIZE = getIntent().getStringExtra("POSTER_IMAGE_SIZE");
        final String BACK_DROP_IMAGE_SIZE = getIntent().getStringExtra("BACK_DROP_IMAGE_SIZE");

        setContentView(R.layout.activity_movie_detail);

        TextView title = (TextView) findViewById(R.id.tvMovieDetailTitle);
        TextView overview = (TextView) findViewById(R.id.tvMovieDetailOverview);
        TextView releaseDate = (TextView) findViewById(R.id.tvMovieDetailReleaseDateVal);
        ImageView imageView = (ImageView) findViewById(R.id.ivMovieDetailImage);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rbMovieDetail);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
        ratingBar.setRating((float) movie.getVoteAverage());

        String imageUri = imageBaseURL +
                (movie.getBackdropImagePath() != null && ! "null".equals(movie.getBackdropImagePath()) ?
                        BACK_DROP_IMAGE_SIZE + "/" + movie.getBackdropImagePath() :
                        POSTER_IMAGE_SIZE + "/" +movie.getPosterImagePath());
        Picasso.with(this).load(imageUri).into(imageView);
    }
}
