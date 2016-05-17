package com.codepath.flickster.requests;

import com.codepath.flickster.requests.base.BaseFlicksterRequest;


public class NowPlayingRequest extends BaseFlicksterRequest {

    private static final String PATH = "movie/now_playing";

    public String getPath() {
        return PATH;
    }
}
