package com.amazon.popmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiamingm on 12/3/16.
 */
public class DiscoverMoviesLoaderCallbacks
        implements LoaderManager.LoaderCallbacks<List<Movie>> {
    private LoaderManager mLoaderManager;
    private LoaderManager mSupportLoaderManager;
    private Context mContext;
    private ImageGridAdapter mImageGridAdapter;
    private List<Bitmap> mBitmapList;
    private int mMovieListSize = MainDiscoveryFragment.CACHE_IMAGE_BATCH_SIZE;
    private Map<Integer, CacheImageLoaderCallbacks> mCallbacksDict;

    public DiscoverMoviesLoaderCallbacks(LoaderManager loaderManager,
                                         LoaderManager supportLoaderManager,
                                         Context context,
                                         ImageGridAdapter imageGridAdapter,
                                         List<Bitmap> bitmapList) {
        mLoaderManager = loaderManager;
        mSupportLoaderManager = supportLoaderManager;
        mContext = context;
        mImageGridAdapter = imageGridAdapter;
        mBitmapList = bitmapList;
        mCallbacksDict = new HashMap<>();
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        String sortBy = args.getString(DiscoverMoviesLoader.SORT_BY_KEY);
        if (sortBy == null ||
                DiscoverMoviesLoader.TYPE_POPULAR.compareTo(sortBy) != 0 &&
                DiscoverMoviesLoader.TYPE_TOP_RATED.compareTo(sortBy) != 0) {
            throw new RuntimeException("Error args: " +
                    DiscoverMoviesLoader.SORT_BY_KEY);
        }
        Loader<List<Movie>> loader = mSupportLoaderManager.getLoader(id);
        if (id == MainDiscoveryFragment.DISCOVER_MOVIE_LOADER_ID) {
            if (loader == null) {
                loader = new DiscoverMoviesLoader(mContext, sortBy);
            }
        } else {
            throw new RuntimeException("Error loader id: " + id);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        if (data != null) {
            if (data.size() < MainDiscoveryFragment.CACHE_IMAGE_BATCH_SIZE) {
                mMovieListSize = data.size();
            }
            for (int i = 0; i < mMovieListSize; ++i) {
                Movie movie = data.get(i);
                Integer id = MainDiscoveryFragment.CACHE_IMAGE_LOADER_START_ID + i;
                CacheImageLoaderCallbacks cacheImageLoaderCallbacks;
                if (mCallbacksDict.containsKey(id)) {
                    cacheImageLoaderCallbacks = mCallbacksDict.get(id);
                } else {
                    cacheImageLoaderCallbacks = new CacheImageLoaderCallbacks(
                            mSupportLoaderManager,
                            mContext,
                            movie.getPosterPath(),
                            mImageGridAdapter,
                            mBitmapList,
                            i);
                }
                Loader<Bitmap> bitmapLoader = mLoaderManager.initLoader(
                        id,
                        null,
                        cacheImageLoaderCallbacks);
                ((CacheImageLoader)bitmapLoader).updateImagePath(movie.getPosterPath());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        if (loader != null) {
            mBitmapList.clear();
            mImageGridAdapter.clear();
        }
    }
}
