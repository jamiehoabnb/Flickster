package com.codepath.flickster.requests;

import com.codepath.flickster.requests.base.BaseFlicksterRequest;


public class UpcomingRequest extends BaseFlicksterRequest {

    private static final String PATH = "movie/upcoming";

    public String getPath() {
        return PATH;
    }
}
