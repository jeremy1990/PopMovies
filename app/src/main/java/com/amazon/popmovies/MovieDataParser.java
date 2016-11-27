package com.amazon.popmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiamingm on 11/27/16.
 */
public class MovieDataParser {
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
}
