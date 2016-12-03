package com.amazon.popmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by jiamingm on 12/3/16.
 */
public class CacheImageLoader extends AsyncTaskLoader<Bitmap> {
    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185/";
    private Context mContext;
    private String mImagePath = "";
    private boolean mNeedToUpdate = true;

    public CacheImageLoader(Context context, String imagePath) {
        super(context);
        mContext = context;
        mImagePath = getImagePath(imagePath);
    }

    @Override
    public Bitmap loadInBackground() {
        try {
            if (mImagePath != null) {
                return Picasso.with(mContext).load(BASE_IMG_URL + mImagePath).get();
            }
        } catch (IOException e) {
            Log.e(CacheImageLoader.class.getSimpleName(), "Cache image error with picasso.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mNeedToUpdate) {
            forceLoad();
        }
    }

    public void updateImagePath(String imagePath) {
        mImagePath = getImagePath(imagePath);
        if (mNeedToUpdate) {
            forceLoad();
        }
    }

    private String getImagePath(String imagePath) {
        mNeedToUpdate = false;
        if (imagePath.compareTo(mImagePath) != 0) {
            mNeedToUpdate = true;
        }
        return imagePath;
    }
}
