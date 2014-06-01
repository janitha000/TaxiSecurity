package com.example.taxisecurity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;

import android.content.Intent;

import android.content.Context;

//9b7ad87f6f86a334b6d86f49c8cebc43fef29471


// 6bb5b35e23bd6cdbcb4dd980cdcdd9cc2754e2ad
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

public class PlacesAutocompleteActivity extends Activity implements AdapterView.OnItemClickListener  {
	
	String str;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
       
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.list_item);
	    
        AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.placesauto));
        
        
        autoCompView.setOnItemClickListener(this);
        autoCompView.setThreshold(1);

	
	
	
	
		

	
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position,
			long id) {
		str = (String) adapterView.getItemAtPosition(position);
		Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

	    try {
	        ArrayList<Address> adresses = (ArrayList<Address>) geocoder.getFromLocationName(str, 10);
	        for(Address add : adresses){
	                double longitude = add.getLongitude();
	                double latitude = add.getLatitude();
	                
	                Intent intent = new Intent(PlacesAutocompleteActivity.this, averseService.class);
	                
	                intent.putExtra("Latitiude", latitude);
	                intent.putExtra("Longtitude",longitude);
	                
	                startService(intent);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } 
		catch(IllegalArgumentException ex){
	        ex.printStackTrace();
	    }
		
	}
}
	

