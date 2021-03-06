package com.amazon.popmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainDiscoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);
        if (savedInstanceState == null) {
            MainDiscoveryFragment mainDiscoveryFragment = new MainDiscoveryFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mainDiscoveryFragment)
                    .commit();
        }
    }
}
