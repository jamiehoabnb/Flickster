package com.codepath.flickster.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private String imageBaseURL;
    private static final String POSTER_IMAGE_SIZE = "w185";
    private static final String BACK_DROP_IMAGE_SIZE = "w300";

    public MovieAdapter(Context context, List<Movie> movies, String imageBaseURL) {
        super(context, 0, movies);
        this.imageBaseURL = imageBaseURL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvMovieTitle);
        TextView tvOverview = (TextView) convertView.findViewById(R.id.tvMovieOverview);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());


        ImageView ivBasicImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);

        if (ivBasicImage != null) {
            String imageUri = imageBaseURL + POSTER_IMAGE_SIZE + "/" + movie.getPosterImagePath();
            Picasso.with(getContext()).load(imageUri).into(ivBasicImage);
        } else {
            ivBasicImage = (ImageView) convertView.findViewById(R.id.ivMovieImageLand);
            String imageUri = imageBaseURL + BACK_DROP_IMAGE_SIZE + "/" + movie.getBackdropImagePath();
            Picasso.with(getContext()).load(imageUri).into(ivBasicImage);
        }

        return convertView;
    }
}
