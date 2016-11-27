package com.amazon.popmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by jiamingm on 11/27/16.
 */
public class CacheImageTask extends AsyncTask<String, Void, Bitmap> {
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
        if (urls != null && urls.length == 1) {
            return getHttpBitmap(urls[0]);
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
