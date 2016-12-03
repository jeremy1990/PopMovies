package com.amazon.popmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amazon.popmovies.databinding.FragmentDetailBinding;
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
        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(DetailActivity.MOVIE_DATA)) {
            Movie movie = intent.getParcelableExtra(DetailActivity.MOVIE_DATA);
            if (movie != null) {
                Picasso.with(getActivity()).load(BASE_IMG_URL + movie.getPosterPath()).into(binding.poster);
                binding.originalTitle.setText(movie.getOriginalTitle());
                SimpleDateFormat format = new SimpleDateFormat(getString(R.string.release_date_format));
                binding.releaseDate.setText(format.format(movie.getReleaseDate()));
                binding.rating.setText(String.valueOf((movie.getVoteAverage())));
                binding.overview.setText(movie.getOverview());
            }
        }
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}
