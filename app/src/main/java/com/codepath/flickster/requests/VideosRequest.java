package com.codepath.flickster.requests;

import com.codepath.flickster.requests.base.BaseFlicksterRequest;
import com.google.api.services.youtube.model.VideoRating;


public class VideosRequest extends BaseFlicksterRequest {

    private static final String PATH_START = "movie";

    private static final String PATH_END = "videos";

    private long id;

    public VideosRequest(long id) {
        this.id = id;
    }

    public String getPath() {
        StringBuilder path = new StringBuilder();
        path.append(PATH_START)
                .append('/')
                .append(id)
                .append('/')
                .append(PATH_END);
        return path.toString();
    }
}
