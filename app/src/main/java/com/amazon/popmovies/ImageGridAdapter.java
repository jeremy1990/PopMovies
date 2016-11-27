package com.amazon.popmovies;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by jiamingm on 11/27/16.
 */
public class ImageGridAdapter extends ArrayAdapter<Bitmap> {
    private Context mContext;
    private int mLayoutResourceId;
    private List<Bitmap> mData;

    public ImageGridAdapter(Context context, int layoutResourceId, List<Bitmap> data) {
        super(context, layoutResourceId, data);
        if (context instanceof Activity) {
            mContext = context;
            mLayoutResourceId = layoutResourceId;
            mData = data;
        } else {
            throw new RuntimeException(ImageGridAdapter.class.getSimpleName()
                    + ": Error Type of context");
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView holder;
        if (view == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            view = inflater.inflate(mLayoutResourceId, viewGroup, false);
            holder = (ImageView) view.findViewById(R.id.main_discovery_item);
            view.setTag(holder);
        } else {
            holder = (ImageView) view.getTag();
        }
        Bitmap bitmap = mData.get(i);
        holder.setImageBitmap(bitmap);
        return view;
    }
}
