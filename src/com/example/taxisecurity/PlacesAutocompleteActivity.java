package com.example.taxisecurity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class PlacesAutocompleteActivity extends Activity implements AdapterView.OnItemClickListener {
	
	String str;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
       
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.list_item);
	    PlacesAutoCompleteAdapter adapter = new PlacesAutoCompleteAdapter(PlacesAutocompleteActivity.this, R.layout.placesautocomplete);
        AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        autoCompView.setAdapter(adapter);
        
        
        autoCompView.setOnItemClickListener( this);
        autoCompView.setThreshold(1);

        
	
	
//	autoCompView.setOnItemClickListener(new OnItemClickListener() {
//	
//	@Override
//	public void onItemClick(AdapterView<?> adapterView, View view, int position,
//			long id) {
//		Toast.makeText(PlacesAutocompleteActivity.this,"Clicked", Toast.LENGTH_SHORT).show();
//		str = (String) adapterView.getItemAtPosition(position);
//        Context context = null;
		//Toast.makeText(getApplicationContext(),(CharSequence)adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		
	//Geocoder geocoder = new Geocoder(context, Locale.getDefault());

//    try {
//        ArrayList<Address> adresses = (ArrayList<Address>) geocoder.getFromLocationName(str, 10);
//        for(Address add : adresses){
//                double longitude = add.getLongitude();
//                double latitude = add.getLatitude();
//                
//                Intent intent = new Intent(PlacesAutocompleteActivity.this, averseService.class);
//                
//                intent.putExtra("Latitiude", latitude);
//                intent.putExtra("Longtitude",longitude);
//                
//                startActivity(intent);
//        }
//    } catch (IOException e) {
//        e.printStackTrace();
//    } 
//	catch(IllegalArgumentException ex){
//        ex.printStackTrace();
//    }
//	}
//	});
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
	                Toast.makeText(PlacesAutocompleteActivity.this,latitude + " " + longitude, Toast.LENGTH_SHORT).show();
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
	

