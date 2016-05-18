package com.codepath.flickster.activities;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.Movie;
import com.codepath.flickster.util.YouTubeUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends YouTubeBaseActivity {

    @BindView(R.id.tvMovieDetailTitle) TextView title;
    @BindView(R.id.tvMovieDetailOverview) TextView overview;
    @BindView(R.id.tvMovieDetailReleaseDateVal) TextView releaseDate;
    @BindView(R.id.rbMovieDetail) RatingBar ratingBar;
    @BindView(R.id.movieDetailPlayer) YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
        ratingBar.setRating((float) movie.getVoteAverage());

        YouTubeUtil.init(movie, youTubePlayerView, true);
    }
}
