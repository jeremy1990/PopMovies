package com.amazon.popmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by jiamingm on 11/27/16.
 * Please use CacheImageLoader instead
 * Since the life cycles of the instances of this Class is not good managed by the activity or fragment
 * and causes a heavy resource consumption
 */
@Deprecated
public class CacheImageTask extends AsyncTask<String, Void, Bitmap> {
    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185/";

    private ImageGridAdapter mImageGridAdapter;
    private List<Bitmap> mBitmapList;
    private int mIndex;

    public CacheImageTask(ImageGridAdapter imageGridAdapter, List<Bitmap> bitmapList, int index) {
        mImageGridAdapter = imageGridAdapter;
        mBitmapList = bitmapList;
        mIndex = index;
    }

    @Override
    protected Bitmap doInBackground(String...urls) {
        try {
            if (urls != null && urls.length == 1) {
                // old way: return getHttpBitmap(BASE_IMG_URL + urls[0]);
                return Picasso.with(mImageGridAdapter.getContext()).load(BASE_IMG_URL + urls[0]).get();
            }
        } catch (IOException e) {
            Log.e(CacheImageTask.class.getSimpleName(), "Cache image error with picasso.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            mBitmapList.set(mIndex, bitmap);
            mImageGridAdapter.notifyDataSetChanged();
        } else {
            Log.v(CacheImageTask.class.getSimpleName(), "Empty Bitmap");
        }
    }

    @Deprecated
    private Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)myFileURL.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch(Exception e){
            Log.e(CacheImageTask.class.getSimpleName(), "get bitmap error.");
            e.printStackTrace();
        }
        return bitmap;
    }
}
