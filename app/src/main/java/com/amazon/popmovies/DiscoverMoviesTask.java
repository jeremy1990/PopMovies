package com.amazon.popmovies;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiamingm on 11/27/16.
 */
public class DiscoverMoviesTask extends AsyncTask<String, Void, List<Movie>> {
    private static final String LOG_TAG = DiscoverMoviesTask.class.getSimpleName();
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String TYPE_POPULAR = "popular";
    private static final String TYPE_TOP_RATED = "top_rated";
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_PAGE = "page";

    private ImageGridAdapter mImageGridAdapter;
    private List<Bitmap> mBitmapList;
    private List<Movie> mMovieList;

    public DiscoverMoviesTask(ImageGridAdapter imageGridAdapter,
                              List<Bitmap> bitmapList) {
        mImageGridAdapter = imageGridAdapter;
        mBitmapList = bitmapList;
        mMovieList = new ArrayList<>();
    }

    /**
     * @param params
     *  params[0] popular or top_rated
     *  params[1] page default is 1
     * @return
     */
    @Override
    protected List<Movie> doInBackground(String...params) {
        List<Movie> movieList = null;
        if (params != null && params.length >= 1 && params.length <= 2) {
            String discoveryType = params[0];
            if (TYPE_POPULAR.compareTo(discoveryType) != 0 &&
                    TYPE_TOP_RATED.compareTo(discoveryType) != 0) {
                throw new RuntimeException(DiscoverMoviesTask.class.getSimpleName() + ": error discoveryType");
            }
            int page = 1;
            if (params.length == 2) {
                page = Integer.parseInt(params[1]);
                if (page <= 0) {
                    page = 1;
                }
            }

            Uri uri = Uri.parse(BASE_URL + discoveryType)
                    .buildUpon()
                    .appendQueryParameter(KEY_API_KEY, API_KEY)
                    .appendQueryParameter(KEY_PAGE, String.valueOf(page))
                    .build();
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(uri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                if (stringBuffer.length() == 0) {
                    return null;
                }
                movieList = MovieDataParser.getMovieList(stringBuffer.toString());
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, " parse URL error.");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(LOG_TAG, " open connection error.");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(LOG_TAG, " parse json data error.");
                e.printStackTrace();
            } catch (ParseException e) {
                Log.e(LOG_TAG, " parse date error.");
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.e(DiscoverMoviesTask.class.getSimpleName(), " close buffer reader error.");
                        e.printStackTrace();
                    }
                }
            }
        }
        return movieList;
    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {
        super.onPostExecute(movieList);
        if (movieList != null) {
            mMovieList = movieList;
            for (int i = 0; i < movieList.size(); ++i) {
                Movie movie = movieList.get(i);
                new CacheImageTask(
                        mImageGridAdapter, mBitmapList, i).execute(movie.getPosterPath());
            }
        }
    }

    public List<Movie> getMovieList() {
        return mMovieList;
    }
}
