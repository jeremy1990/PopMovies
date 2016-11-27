package com.amazon.popmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jiamingm on 11/27/16.
 */
public class CacheImageTask {
    private ImageGridAdapter mImageGridAdapter;

    public CacheImageTask(ImageGridAdapter imageGridAdapter) {
        mImageGridAdapter = imageGridAdapter;
    }

    public Bitmap[] fetchBitmaps(String[]...urls) {
        Bitmap[] result = null;
        if (urls != null && urls.length == 1) {
            String[] inputs = urls[0];
            result = new Bitmap[inputs.length];
            for (int i = 0; i < inputs.length; ++i) {
                //Log.d(CacheImageTask.class.getSimpleName(), inputs[i]);
                result[i] = getHttpBitmap(inputs[i]);
            }
        }
        return result;
    }

    public void updateAdapter(Bitmap []bitmaps) {
        if (bitmaps != null) {
            mImageGridAdapter.clear();
            mImageGridAdapter.addAll(bitmaps);
        } else {
            Log.v(CacheImageTask.class.getSimpleName(), "Empty Bitmaps");
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
