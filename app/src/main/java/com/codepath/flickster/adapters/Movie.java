package com.codepath.flickster.adapters;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Movie implements Serializable {

    private long id;
    private String title;
    private String overview;
    private String posterImagePath;
    private String backdropImagePath;
    private double voteAverage;
    private String releaseDate;

    public enum Type {
        LESS_POPULAR(0), POPULAR(1);

        int val;
        Type(int val) {
            this.val = val;
        }

        public int getVal() { return val; }
    }

    public Movie(JSONObject movieJSON) throws JSONException {
        id = movieJSON.getLong("id");
        title = movieJSON.getString("title");
        overview = movieJSON.getString("overview");
        posterImagePath = movieJSON.getString("poster_path");
        backdropImagePath = movieJSON.getString("backdrop_path");
        voteAverage = movieJSON.getDouble("vote_average");
        releaseDate = movieJSON.getString("release_date");
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

    public double getVoteAverage() { return voteAverage; }

    public Type getType() { return voteAverage > 5 ? Type.POPULAR : Type.LESS_POPULAR; }

    public String getReleaseDate() { return releaseDate; }
}
