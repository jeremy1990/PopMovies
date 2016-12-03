package com.amazon.popmovies;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jiamingm on 11/29/16.
 */
public class MovieItemOnItemClickListener implements AdapterView.OnItemClickListener {
    private Context mContext;
    private List<Movie> mMovieList;

    public MovieItemOnItemClickListener(final Context context) {
        mContext = context;
    }

    public void updateMovieList(final List<Movie> movieList) {
        mMovieList = movieList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mMovieList != null && mMovieList.size() > i) {
            Movie movie = mMovieList.get(i);
            if (movie != null) {
                Toast toast = Toast.makeText(mContext, movie.getOriginalTitle(), Toast.LENGTH_SHORT);
                toast.show();

                Intent detailIntent = new Intent(mContext, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.MOVIE_DATA, movie);
                mContext.startActivity(detailIntent);
            }
        }
    }
}
