package com.codepath.flickster.activities;

import android.os.Bundle;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.util.YouTubeUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YouTubeActivity extends YouTubeBaseActivity {

    @BindView(R.id.player) YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        ButterKnife.bind(this);

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        YouTubeUtil.init(movie, youTubePlayerView, false);
    }

}