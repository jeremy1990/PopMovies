package com.amazon.popmovies;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainDiscoveryFragment extends Fragment {
    private ImageGridAdapter mDiscoveryMoviesAdapter;
    private List<Bitmap> mBitmapList;

    public MainDiscoveryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the placeholder
        Resources resources = getResources();
        Bitmap placeholder = BitmapFactory.decodeResource(resources, R.drawable.placeholder);
        mBitmapList = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            mBitmapList.add(placeholder);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        new DiscoverMoviesTask(mDiscoveryMoviesAdapter, mBitmapList)
                .execute("top_rated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDiscoveryMoviesAdapter = new ImageGridAdapter(
                getActivity(),
                R.layout.main_discovery_item,
                mBitmapList);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_discovery, container, false);
        GridView discoveryGrid = (GridView) rootView.findViewById(R.id.discovery_grid);
        discoveryGrid.setAdapter(mDiscoveryMoviesAdapter);

        return rootView;
    }
}
