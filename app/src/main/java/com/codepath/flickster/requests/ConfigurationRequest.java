package com.codepath.flickster.requests;

import com.codepath.flickster.requests.base.BaseFlicksterRequest;


public class ConfigurationRequest extends BaseFlicksterRequest {

    private static final String PATH = "configuration";

    public String getPath() {
        return PATH;
    }
}
