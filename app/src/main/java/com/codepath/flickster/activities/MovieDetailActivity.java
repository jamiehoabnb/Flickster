package com.codepath.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private class ImageOnClickListener implements AdapterView.OnClickListener {
        private Movie movie;

        private ImageOnClickListener(Movie movie) {
            this.movie = movie;
        }

        @Override
        public void onClick(View item) {
            Intent intent = new Intent(getApplicationContext(), YouTubeActivity.class);
            intent.putExtra("movie", movie);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        String imageBaseURL = getIntent().getStringExtra("imageBaseURL");
        final String POSTER_IMAGE_SIZE = getIntent().getStringExtra("POSTER_IMAGE_SIZE");
        final String BACK_DROP_IMAGE_SIZE = getIntent().getStringExtra("BACK_DROP_IMAGE_SIZE");

        setContentView(R.layout.activity_movie_detail);

        TextView title = (TextView) findViewById(R.id.tvMovieDetailTitle);
        TextView overview = (TextView) findViewById(R.id.tvMovieDetailOverview);
        TextView releaseDate = (TextView) findViewById(R.id.tvMovieDetailReleaseDateVal);
        ImageView imageView = (ImageView) findViewById(R.id.ivMovieDetailImage);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rbMovieDetail);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
        ratingBar.setRating((float) movie.getVoteAverage());

        imageView.setOnClickListener(new ImageOnClickListener(movie));

        //Use poster image if movie does not have backdrop image.
        String imageUri = imageBaseURL +
                (movie.getBackdropImagePath() != null && ! "null".equals(movie.getBackdropImagePath()) ?
                        BACK_DROP_IMAGE_SIZE + "/" + movie.getBackdropImagePath() :
                        POSTER_IMAGE_SIZE + "/" +movie.getPosterImagePath());
        Picasso.with(this).load(imageUri)
                .placeholder(R.drawable.ic_movie_placeholder).into(imageView);
    }
}
