package com.codepath.flickster.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.Movie;
import com.codepath.flickster.adapters.MovieAdapter;
import com.codepath.flickster.requests.ConfigurationRequest;
import com.codepath.flickster.requests.NowPlayingRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private static String imageBaseURL;

    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.lvMovies) ListView listView;

    private MovieAdapter adapter;

    private ResponseHandlerInterface configResponseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                imageBaseURL = response.getJSONObject("images").getString("base_url");
            } catch (JSONException e) {
                Log.e(MovieActivity.class.getName(), "Error getting configuration.", e);
            }

            new NowPlayingRequest().execRequest(nowPlayingResponseHandler);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Log.e(MovieActivity.class.getName(), "Error getting configuration", throwable);
        }
    };

    private ResponseHandlerInterface nowPlayingResponseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);

            try {
                JSONArray results = response.getJSONArray("results");
                List<Movie> movies = new ArrayList<Movie>();

                for (int i = 0; i < results.length(); i++) {
                    movies.add(new Movie(results.getJSONObject(i)));
                }

                if (adapter == null) {
                    adapter = new MovieAdapter(getBaseContext(), movies, imageBaseURL,
                            new MovieAdapter.DetailClickListener() {

                                @Override
                                public void onClick(Movie movie, Context context) {
                                    Intent intent = movie.getType() == Movie.Type.POPULAR ?
                                            new Intent(context, YouTubeActivity.class) :
                                            new Intent(context, MovieDetailActivity.class);

                                    intent.putExtra("movie", movie);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }
                            });
                    listView.setAdapter(adapter);
                } else {
                    adapter.refresh(movies);
                    swipeContainer.setRefreshing(false);
                }
            } catch (JSONException e) {
                Log.e(MovieActivity.class.getName(), "Error getting now playing list.", e);
                swipeContainer.setRefreshing(false);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Log.e(MovieActivity.class.getName(), "Error getting now playing list.", throwable);
            swipeContainer.setRefreshing(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new NowPlayingRequest().execRequest(nowPlayingResponseHandler);
            }
        });

        new ConfigurationRequest().execRequest(configResponseHandler);
    }
}
