package com.codepath.flickster.activities;

import android.os.Bundle;
import android.util.Log;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.Movie;
import com.codepath.flickster.requests.VideosRequest;
import com.codepath.flickster.util.YouTubeUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class YouTubeActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
        YouTubeUtil.init(movie, youTubePlayerView, false);
    }

}
