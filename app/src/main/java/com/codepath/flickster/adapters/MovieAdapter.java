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
    private static final String BACK_DROP_IMAGE_SIZE = "w500";

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivBasicImage;
        ImageView ivBasicImageLand;
    }


    public MovieAdapter(Context context, List<Movie> movies, String imageBaseURL) {
        super(context, 0, movies);
        this.imageBaseURL = imageBaseURL;
    }

    public void refresh(List<Movie> movies) {
        this.clear();
        this.addAll(movies);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvMovieTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvMovieOverview);
            viewHolder.ivBasicImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.ivBasicImageLand = (ImageView) convertView.findViewById(R.id.ivMovieImageLand);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        if (viewHolder.ivBasicImage != null) {
            String imageUri = imageBaseURL + POSTER_IMAGE_SIZE + "/" + movie.getPosterImagePath();
            Picasso.with(getContext()).load(imageUri).into(viewHolder.ivBasicImage);
        } else {
            String imageUri = imageBaseURL + BACK_DROP_IMAGE_SIZE + "/" +
                    (movie.getBackdropImagePath() != null && ! "null".equals(movie.getBackdropImagePath()) ?
                        movie.getBackdropImagePath() : movie.getPosterImagePath());
            Picasso.with(getContext()).load(imageUri).into(viewHolder.ivBasicImageLand);
        }

        return convertView;
    }
}
