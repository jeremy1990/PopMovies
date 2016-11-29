package com.amazon.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
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
                TextView textView = (TextView) rootView.findViewById(R.id.default_text);
                textView.setText(movie.getOverview());
            }
        }
        // Inflate the layout for this fragment
        return rootView;
    }
}
