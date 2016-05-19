package com.codepath.flickster.util;

import android.util.Log;

import com.codepath.flickster.activities.MovieActivity;
import com.codepath.flickster.activities.YouTubeActivity;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.requests.VideosRequest;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class YouTubeUtil {

    private static final String GOOGLE_ANDROID_KEY = "AIzaSyAm8LDhPbCcS_04pwkdTv5RTUokuOPf1q8";

    private static class VideosResponseHandler extends JsonHttpResponseHandler {

        private YouTubePlayer youTubePlayer;
        private Movie movie;
        private boolean cue;

        public VideosResponseHandler(Movie movie, YouTubePlayer youTubePlayer, boolean cue) {
            this.movie = movie;
            this.youTubePlayer = youTubePlayer;
            this.cue = cue;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                JSONArray results = response.getJSONArray("results");
                if (results.length() > 0) {
                    JSONObject video = results.getJSONObject(0);
                    String key = video.getString("key");

                    if (cue) {
                        youTubePlayer.cueVideo(key);
                    } else {
                        youTubePlayer.loadVideo(key);
                    }
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

    private static class YouTubeInitListener implements YouTubePlayer.OnInitializedListener {

        private Movie movie;
        private boolean cue;

        private YouTubeInitListener(Movie movie, boolean cue) {
            this.movie =  movie;
            this.cue = cue;
        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                            YouTubePlayer youTubePlayer, boolean b) {
            new VideosRequest(movie.getId()).execRequest(new VideosResponseHandler(movie, youTubePlayer, cue));
        }
        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                            YouTubeInitializationResult youTubeInitializationResult) {
            Log.e(YouTubeActivity.class.getName(), "YouTubePlayer Initialization error: "
                    + youTubeInitializationResult);
        }
    };

    public static void init(Movie movie, YouTubePlayerView youTubePlayerView, boolean cue) {
        youTubePlayerView.initialize(GOOGLE_ANDROID_KEY, new YouTubeInitListener(movie, cue));
    }
}
