package com.example.taxisecurity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class mapActivity extends FragmentActivity {
	
	GoogleMap Mmap;
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_map);
	    
      MapFragment mapFragment = ((MapFragment) this
               .getFragmentManager().findFragmentById(R.id.map));
		Mmap= ((MapFragment) this.getFragmentManager().findFragmentById(R.id.map)).getMap();
		Mmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		Mmap.setMyLocationEnabled(true);
}}

