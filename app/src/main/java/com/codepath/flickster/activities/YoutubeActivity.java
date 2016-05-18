package com.codepath.flickster.activities;

import android.os.Bundle;
import android.util.Log;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.Movie;
import com.codepath.flickster.requests.VideosRequest;
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

    private static final String GOOGLE_ANDROID_KEY = "AIzaSyAm8LDhPbCcS_04pwkdTv5RTUokuOPf1q8";

    private class VideosResponseHandler extends JsonHttpResponseHandler {

        private YouTubePlayer youTubePlayer;
        private Movie movie;

        public VideosResponseHandler(Movie movie, YouTubePlayer youTubePlayer) {
            this.movie = movie;
            this.youTubePlayer = youTubePlayer;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                JSONArray results = response.getJSONArray("results");
                if (results.length() > 0) {
                    JSONObject video = results.getJSONObject(0);
                    String key = video.getString("key");
                    youTubePlayer.loadVideo(key);
                }
            } catch (JSONException e) {
                Log.e(MovieActivity.class.getName(), "Error getting videos for " + movie.getId() + ".", e);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Log.e(MovieActivity.class.getName(), "Error getting videos for " + movie.getId() + ".", throwable);
        }
    };

    private class YouTubeInitListener implements YouTubePlayer.OnInitializedListener {

        private Movie movie;

        private YouTubeInitListener(Movie movie) {
            this.movie =  movie;
        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                YouTubePlayer youTubePlayer, boolean b) {
            new VideosRequest(movie.getId()).execRequest(new VideosResponseHandler(movie, youTubePlayer));
        }
        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider,
                YouTubeInitializationResult youTubeInitializationResult) {
            Log.e(YouTubeActivity.class.getName(), "YouTubePlayer Initialization error: "
                    + youTubeInitializationResult);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(GOOGLE_ANDROID_KEY, new YouTubeInitListener(movie));
    }

}
