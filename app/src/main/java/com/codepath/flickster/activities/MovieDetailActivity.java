package com.codepath.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.Movie;
import com.codepath.flickster.util.YouTubeUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        setContentView(R.layout.activity_movie_detail);

        TextView title = (TextView) findViewById(R.id.tvMovieDetailTitle);
        TextView overview = (TextView) findViewById(R.id.tvMovieDetailOverview);
        TextView releaseDate = (TextView) findViewById(R.id.tvMovieDetailReleaseDateVal);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rbMovieDetail);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
        ratingBar.setRating((float) movie.getVoteAverage());

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.movieDetailPlayer);
        YouTubeUtil.init(movie, youTubePlayerView, true);
    }
}
