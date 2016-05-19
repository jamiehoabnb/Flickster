package com.codepath.flickster.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.flickster.R;
import com.codepath.flickster.activities.MovieActivity;
import com.codepath.flickster.activities.MovieDetailActivity;
import com.codepath.flickster.activities.YouTubeActivity;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.adapters.MovieAdapter;
import com.codepath.flickster.requests.ConfigurationRequest;
import com.codepath.flickster.requests.NowPlayingRequest;
import com.codepath.flickster.requests.base.BaseFlicksterRequest;
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

public abstract class BaseFragment extends Fragment {

    private static String imageBaseURL;

    SwipeRefreshLayout swipeContainer;

    ListView listView;

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

            getRequest().execRequest(movieResponseHandler);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Log.e(MovieActivity.class.getName(), "Error getting configuration", throwable);
        }
    };

    private ResponseHandlerInterface movieResponseHandler = new JsonHttpResponseHandler() {
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
                    adapter = new MovieAdapter(listView.getContext(), movies, imageBaseURL,
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

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(getSwipeContainerLayout());
        listView = (ListView) view.findViewById(getListViewId());

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRequest().execRequest(movieResponseHandler);
            }
        });

        new ConfigurationRequest().execRequest(configResponseHandler);
        return view;
    }

    public abstract int getLayout();

    public abstract int getListViewId();

    public abstract int getSwipeContainerLayout();

    public abstract BaseFlicksterRequest getRequest();
}
