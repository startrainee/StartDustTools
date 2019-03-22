package com.stardust.tools.startdusttools.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stardust.tools.startdusttools.R;

public class MapActivity extends AppCompatActivity {

    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        mCurrentLat = intent.getDoubleExtra("lat",0.0);
        mCurrentLon = intent.getDoubleExtra("lon",0.0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setMyLocationMarkerInMap();
    }

    private void setMyLocationMarkerInMap() {

    }
}
