package com.example.taxisecurity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class PlacesAutocompleteActivity extends Activity {
	
	String str;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
       
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.list_item);
	    
        AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
        
        
        //autoCompView.setOnItemClickListener(this);
        autoCompView.setThreshold(1);

	
	
	
	autoCompView.setOnItemClickListener(new OnItemClickListener() {
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position,
			long id) {
		str = (String) adapterView.getItemAtPosition(position);
        Context context = null;
		Toast.makeText(context, "Position clicked: " + position, Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		
	Geocoder geocoder = new Geocoder(context, Locale.getDefault());

    try {
        ArrayList<Address> adresses = (ArrayList<Address>) geocoder.getFromLocationName(str, 10);
        for(Address add : adresses){
                double longitude = add.getLongitude();
                double latitude = add.getLatitude();
        }
    } catch (IOException e) {
        e.printStackTrace();
    } 
	catch(IllegalArgumentException ex){
        ex.printStackTrace();
    }
	}
	});
	}
}
	

