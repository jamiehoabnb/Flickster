package com.codepath.flickster.adapters;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.util.DeviceDimensionsHelper;
import com.codepath.flickster.util.FlicksterConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieAdapter extends ArrayAdapter<Movie> {

    static class ViewHolder {
        @Nullable @BindView(R.id.tvMovieTitle) TextView tvTitle;
        @Nullable @BindView(R.id.tvMovieOverview) TextView tvOverview;
        @BindView(R.id.ivMovieImage) ImageView ivBasicImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface DetailClickListener {
        void onClick(Movie movie, Context context);
    }

    private DetailClickListener detailClickListener;

    public MovieAdapter(Context context, List<Movie> movies, DetailClickListener detailClickListener) {
        super(context, 0, movies);
        this.detailClickListener = detailClickListener;
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
            int type = getItemViewType(position);

            convertView = getInflatedLayoutForType(type, parent);
            viewHolder = new ViewHolder(convertView);
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
                        detailClickListener.onClick(movie, getContext());
                    }
                });

        String imageUri = movie.getType() == Movie.Type.POPULAR ?
                getBackDropImageUri(movie) : getPosterImageUri(movie);


        int width = DeviceDimensionsHelper.getDisplayWidth(getContext());

        if (movie.getType() == Movie.Type.POPULAR) {
            //Backdrop only has title and overview next to it if in landscape mode.
            if (viewHolder.tvTitle != null) {
                width *= FlicksterConstants.BACKDROP_SCREEN_WIDTH_FACTOR;
            }
        } else {
            //Poster always has title and overview next to it.
            width *= FlicksterConstants.POSTER_SCREEN_WIDTH_FACTOR;
        }
        Picasso.with(getContext())
                .load(imageUri)
                .placeholder(R.drawable.placeholder)
                .resize(width, 0)
                .transform(
                        new RoundedCornersTransformation(FlicksterConstants.ROUNDED_CORNER_CONST,
                                FlicksterConstants.ROUNDED_CORNER_CONST))
                .into(viewHolder.ivBasicImage);

        return convertView;
    }

    private String getPosterImageUri(Movie movie) {
        return FlicksterConstants.getImageBaseURL() + FlicksterConstants.POSTER_IMAGE_SIZE + "/" + movie.getPosterImagePath();
    }

    private String getBackDropImageUri(Movie movie) {
        return FlicksterConstants.getImageBaseURL() + FlicksterConstants.BACK_DROP_IMAGE_SIZE + "/" + movie.getBackdropImagePath();
    }

    private View getInflatedLayoutForType(int type, ViewGroup parent) {
        if (type == Movie.Type.POPULAR.getVal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_popular, parent, false);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }
    }
}
