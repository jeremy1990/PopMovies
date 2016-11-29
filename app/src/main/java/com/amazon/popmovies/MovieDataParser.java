package com.amazon.popmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiamingm on 11/27/16.
 */
public class MovieDataParser {
    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185/";

    public static List<String> getPosterPath(String jsonString) throws JSONException {
        List<String> postPathList = new ArrayList<>();
        JSONObject movies = new JSONObject(jsonString);
        JSONArray movieArray = movies.getJSONArray("results");
        for (int i = 0; i < movieArray.length(); ++i) {
            JSONObject movie = movieArray.getJSONObject(i);
            postPathList.add(movie.getString("poster_path"));
        }
        return postPathList;
    }

    public static List<Movie> getMovieList(String jsonString) throws JSONException, ParseException {
        List<Movie> movieList = new ArrayList<>();
        JSONObject moviesObject = new JSONObject(jsonString);
        JSONArray movieArray = moviesObject.getJSONArray("results");
        for (int i = 0; i < movieArray.length(); ++i) {
            JSONObject movieObject = movieArray.getJSONObject(i);

            Movie movie = new Movie();
            movie.setId(movieObject.getInt("id"));
            movie.setOriginalTitle(movieObject.getString("original_title"));
            movie.setOverview(movieObject.getString("overview"));
            movie.setPosterPath(BASE_IMG_URL + movieObject.getString("poster_path"));
            movie.setVoteAverage(movieObject.getDouble("vote_average"));
            String releaseDate = movieObject.getString("release_date");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            movie.setReleaseDate(format.parse(releaseDate));

            movieList.add(movie);
        }
        return movieList;
    }
}
