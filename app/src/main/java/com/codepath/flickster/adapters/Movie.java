package com.codepath.flickster.adapters;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {

    private long id;
    private String title;
    private String overview;
    private String posterImagePath;
    private String backdropImagePath;

    public Movie(JSONObject movieJSON) throws JSONException {
        id = movieJSON.getLong("id");
        title = movieJSON.getString("title");
        overview = movieJSON.getString("overview");
        posterImagePath = movieJSON.getString("poster_path");
        backdropImagePath = movieJSON.getString("backdrop_path");
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public String getBackdropImagePath() {
        return backdropImagePath;
    }
}
