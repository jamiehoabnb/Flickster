package com.codepath.flickster.requests;

import com.codepath.flickster.requests.base.BaseFlicksterRequest;


public class PopularRequest extends BaseFlicksterRequest {

    private static final String PATH = "movie/popular";

    public String getPath() {
        return PATH;
    }
}
