package com.codepath.flickster.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.activities.MovieDetailActivity;
import com.codepath.flickster.activities.YouTubeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private String imageBaseURL;
    private static final String POSTER_IMAGE_SIZE = "w500";
    private static final String BACK_DROP_IMAGE_SIZE = "w1280";
    private Context context;

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivBasicImage;
    }


    public MovieAdapter(Context context, List<Movie> movies, String imageBaseURL) {
        super(context, 0, movies);
        this.context = context;
        this.imageBaseURL = imageBaseURL;
    }

    @Override
    public int getViewTypeCount() {
        return Movie.Type.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = getItem(position);
        return movie.getType().getVal();
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
            int type = getItemViewType(position);

            convertView = getInflatedLayoutForType(type, parent);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvMovieTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvMovieOverview);
            viewHolder.ivBasicImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (viewHolder.tvTitle != null) {
            viewHolder.tvTitle.setText(movie.getTitle());
            viewHolder.tvOverview.setText(movie.getOverview());
        }

        viewHolder.ivBasicImage.setTag(position);
        viewHolder.ivBasicImage.setOnClickListener(
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View item) {
                        int position = (Integer) item.getTag();
                        Movie movie = getItem(position);

                        if (movie.getType() == Movie.Type.POPULAR) {
                            Intent intent = new Intent(getContext(), YouTubeActivity.class);
                            intent.putExtra("movie", movie);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                            intent.putExtra("movie", movie);
                            intent.putExtra("imageBaseURL", imageBaseURL);
                            intent.putExtra("POSTER_IMAGE_SIZE", POSTER_IMAGE_SIZE);
                            intent.putExtra("BACK_DROP_IMAGE_SIZE", BACK_DROP_IMAGE_SIZE);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                });

        String imageUri = movie.getType() == Movie.Type.POPULAR ?
                getBackDropImageUri(movie) : getPosterImageUri(movie);
        Picasso.with(getContext()).load(imageUri)
                .placeholder(R.drawable.ic_movie_placeholder).into(viewHolder.ivBasicImage);

        return convertView;
    }

    private String getPosterImageUri(Movie movie) {
        return imageBaseURL + POSTER_IMAGE_SIZE + "/" + movie.getPosterImagePath();
    }

    private String getBackDropImageUri(Movie movie) {
        return imageBaseURL + BACK_DROP_IMAGE_SIZE + "/" + movie.getBackdropImagePath();
    }

    private View getInflatedLayoutForType(int type, ViewGroup parent) {
        if (type == Movie.Type.POPULAR.getVal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_popular, parent, false);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }
    }
}
