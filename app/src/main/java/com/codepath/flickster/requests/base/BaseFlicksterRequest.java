package com.codepath.flickster.requests.base;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public abstract class BaseFlicksterRequest {

    private static final String URL = "https://api.themoviedb.org/3/";

    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    public abstract String getPath();

    public String getURL() {
        StringBuilder str = new StringBuilder();
        str.append(URL)
                .append(getPath())
                .append("?api_key=")
                .append(API_KEY);
        return str.toString();
    }

    public void execRequest(ResponseHandlerInterface responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = getURL();
        client.get(url, responseHandler);
    }
}
