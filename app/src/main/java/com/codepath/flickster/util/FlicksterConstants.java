package com.codepath.flickster.util;

public class FlicksterConstants {

    public static final int ROUNDED_CORNER_CONST = 10;
    public static final String POSTER_IMAGE_SIZE = "w500";
    public static final String BACK_DROP_IMAGE_SIZE = "w1280";
    public static final double POSTER_SCREEN_WIDTH_FACTOR = 0.42;
    public static final double BACKDROP_SCREEN_WIDTH_FACTOR = 0.60;

    private static String imageBaseURL;

    public static String getImageBaseURL() {
        return imageBaseURL;
    }

    public static void setImageBaseURL(String imageBaseURL) {
        FlicksterConstants.imageBaseURL = imageBaseURL;
    }
}
