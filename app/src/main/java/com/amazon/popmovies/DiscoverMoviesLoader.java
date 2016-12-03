package com.amazon.popmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
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
import java.util.List;

/**
 * Created by jiamingm on 12/1/16.
 */
public class DiscoverMoviesLoader extends AsyncTaskLoader<List<Movie>> {
    public static final String SORT_BY_KEY = "sort_by";
    public static final String TYPE_POPULAR = "popular";
    public static final String TYPE_TOP_RATED = "top_rated";
    private static final String LOG_TAG = DiscoverMoviesLoader.class.getSimpleName();
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_PAGE = "page";

    private MovieItemOnItemClickListener mMovieItemOnItemClickListner;
    private Uri mUri;
    private String mSortBy = "";
    private boolean mNeedToUpdate = false;

    public DiscoverMoviesLoader(Context context,
                                MovieItemOnItemClickListener movieItemOnItemClickListener,
                                String...params) {
        super(context);
        mMovieItemOnItemClickListner = movieItemOnItemClickListener;
        if (params != null && params.length >= 1 && params.length <= 2) {
            mUri = getUriFromParams(params);
        }
    }

    @Override
    public List<Movie> loadInBackground() {
        List<Movie> movieList = null;
        if (mUri != null) {
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(mUri.toString());
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
                        Log.e(LOG_TAG, " close buffer reader error.");
                        e.printStackTrace();
                    }
                }
            }
        }
        return movieList;
    }

    @Override
    public void deliverResult(List<Movie> data) {
        super.deliverResult(data);
        if (data != null && mMovieItemOnItemClickListner != null) {
            mMovieItemOnItemClickListner.updateMovieList(data);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mNeedToUpdate) {
            forceLoad();
        }
    }

    public void updateUriFromParams(String...params) {
        mUri = getUriFromParams(params);
        if (mNeedToUpdate) {
            forceLoad();
        }
    }

    private Uri getUriFromParams(String...params) {
        String discoveryType = params[0];
        if (TYPE_POPULAR.compareTo(discoveryType) != 0 &&
                TYPE_TOP_RATED.compareTo(discoveryType) != 0) {
            throw new RuntimeException(DiscoverMoviesLoader.class.getSimpleName() + ": error discoveryType");
        }
        mNeedToUpdate = false;
        if (discoveryType.compareTo(mSortBy) != 0) {
            mSortBy = discoveryType;
            mNeedToUpdate = true;
        }
        int page = 1;
        if (params.length == 2) {
            page = Integer.parseInt(params[1]);
            if (page <= 0) {
                page = 1;
            }
        }

        return Uri.parse(BASE_URL + discoveryType)
                .buildUpon()
                .appendQueryParameter(KEY_API_KEY, API_KEY)
                .appendQueryParameter(KEY_PAGE, String.valueOf(page))
                .build();
    }
}
