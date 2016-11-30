package com.amazon.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/w342/";

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(DetailActivity.MOVIE_DATA)) {
            Movie movie = (Movie) intent.getSerializableExtra(DetailActivity.MOVIE_DATA);
            if (movie != null) {
                ImageView poster = (ImageView) rootView.findViewById(R.id.poster);
                Picasso.with(getActivity()).load(BASE_IMG_URL + movie.getPosterPath()).into(poster);

                TextView originalTitle = (TextView) rootView.findViewById(R.id.original_title);
                originalTitle.setText(movie.getOriginalTitle());

                SimpleDateFormat format = new SimpleDateFormat(getString(R.string.release_date_format));
                TextView releaseDate = (TextView) rootView.findViewById(R.id.release_date);
                releaseDate.setText(format.format(movie.getReleaseDate()));

                TextView rating = (TextView) rootView.findViewById(R.id.rating);
                rating.setText(String.valueOf((movie.getVoteAverage())));

                TextView overview = (TextView) rootView.findViewById(R.id.overview);
                overview.setText(movie.getOverview());
            }
        }
        // Inflate the layout for this fragment
        return rootView;
    }
}
