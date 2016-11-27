package com.amazon.popmovies;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainDiscoveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainDiscoveryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageGridAdapter mDiscoveryMoviesAdapter;

    public MainDiscoveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainDiscoveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainDiscoveryFragment newInstance(String param1, String param2) {
        MainDiscoveryFragment fragment = new MainDiscoveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        String[] movies = {
                "http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                "http://image.tmdb.org/t/p/w185/9HE9xiNMEFJnCzndlkWD7oPfAOx.jpg",
                "http://image.tmdb.org/t/p/w185/xfWac8MTYDxujaxgPVcRD9yZaul.jpg",
                "http://image.tmdb.org/t/p/w185/4Iu5f2nv7huqvuYkmZvSPOtbFjs.jpg"
        };
        new CacheImageTaskProxy(mDiscoveryMoviesAdapter).execute(movies);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDiscoveryMoviesAdapter = new ImageGridAdapter(
                getActivity(),
                R.layout.main_discovery_item,
                new ArrayList<Bitmap>());
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_discovery, container, false);
        GridView discoveryGrid = (GridView) rootView.findViewById(R.id.discovery_grid);
        discoveryGrid.setAdapter(mDiscoveryMoviesAdapter);

        return rootView;
    }

    public class CacheImageTaskProxy extends AsyncTask<String[], Void, Bitmap[]> {
        private CacheImageTask mCacheImageTask;

        public CacheImageTaskProxy(ImageGridAdapter imageGridAdapter) {
            mCacheImageTask = new CacheImageTask(imageGridAdapter);
        }

        @Override
        protected Bitmap[] doInBackground(String[]...params) {
            return mCacheImageTask.fetchBitmaps(params);
        }

        @Override
        protected void onPostExecute(Bitmap[] bitmaps) {
            super.onPostExecute(bitmaps);
            mCacheImageTask.updateAdapter(bitmaps);
        }
    }
}
