package com.amazon.popmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        setHasOptionsMenu(true);

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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = preferences.getString(getString(R.string.pref_title_sort_by),
                getString(R.string.pref_default_sort_by));
        new DiscoverMoviesTask(mDiscoveryMoviesAdapter, mBitmapList)
                .execute(sortBy);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main_discovery, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d(MainDiscoveryFragment.class.getSimpleName(), " failed to start settings activity.");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
