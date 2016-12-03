package com.amazon.popmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.List;

/**
 * Created by jiamingm on 12/3/16.
 */
public class CacheImageLoaderCallbacks
        implements LoaderManager.LoaderCallbacks<Bitmap> {
    private LoaderManager mSupportLoaderManager;
    private Context mContext;
    private String mImagePath;

    private ImageGridAdapter mImageGridAdapter;
    private List<Bitmap> mBitmapList;
    private int mIndex;


    public CacheImageLoaderCallbacks(LoaderManager supportLoaderManager,
                                     Context context,
                                     String imagePath,
                                     ImageGridAdapter imageGridAdapter,
                                     List<Bitmap> bitmapList,
                                     int index) {
        mSupportLoaderManager = supportLoaderManager;
        mContext = context;
        mImagePath = imagePath;

        mImageGridAdapter = imageGridAdapter;
        mBitmapList = bitmapList;
        mIndex = index;
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        Loader<Bitmap> loader = mSupportLoaderManager.getLoader(id);
        if (id >= MainDiscoveryFragment.CACHE_IMAGE_LOADER_START_ID &&
                id <= MainDiscoveryFragment.CACHE_IMAGE_LOADER_START_ID + MainDiscoveryFragment.CACHE_IMAGE_BATCH_SIZE) {
            if (loader == null) {
                loader = new CacheImageLoader(mContext, mImagePath);
            }
        } else {
            throw new RuntimeException("Error loader id: " + id);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {
        if (data != null) {
            mBitmapList.set(mIndex, data);
            mImageGridAdapter.notifyDataSetChanged();
        } else {
            Log.v(CacheImageLoaderCallbacks.class.getSimpleName(), "Empty Bitmap");
        }
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {
        mBitmapList.clear();
    }
}
