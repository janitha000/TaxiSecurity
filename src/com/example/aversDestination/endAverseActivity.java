package com.example.aversDestination;

import com.example.map.mapActivity;
import com.example.sms.smsService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class endAverseActivity extends Activity {
	 String password ;
	 Double Lat;
	 Double Lon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	
		super.onCreate(savedInstanceState);
		Intent gIntent = getIntent();
		final Double Lat = gIntent.getDoubleExtra("DestinationLat",0.0);
		final Double Lon = gIntent.getDoubleExtra("DestinationLon",0.0);
		
		SharedPreferences storage = getSharedPreferences("ContactData",Context.MODE_PRIVATE );
		password = storage.getString("password", "Not Working");
		String rev1Pwd = new StringBuilder(password).reverse().toString();
		Toast.makeText(endAverseActivity.this, rev1Pwd, Toast.LENGTH_LONG).show();
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this); 
		 alert.setView(input);
		 
	    alert.setTitle("Averse Destination Service")
	    .setMessage("To end Averse Destination Service type your password and press OK")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	String value = input.getText().toString();
	        	
	        	if(value.equals(password)){
	        	finish(); //Stops the transparent Activity (Do not remove)
	        	Intent stopIntent = new Intent(endAverseActivity.this, averseService.class);

	        	stopService(stopIntent); //stops the averseService
	        	}
	        	else {
	        		String revPwd = new StringBuilder(password).reverse().toString();
	        		if(revPwd == value){
	        			Intent stopIntent = new Intent(endAverseActivity.this, averseService.class);

	    	        	stopService(stopIntent); //stops the averseService
	        			startService(new Intent(endAverseActivity.this,smsService.class));
	        		}
	        	}
	        	finish(); //Stops the transparent Activity (Do not remove)
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	finish();
	        }
	     })
	     .setNeutralButton("Show In Map", new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				double Deafout=0.0;
				
				
				Toast.makeText(endAverseActivity.this, "lon is " +Lon, Toast.LENGTH_LONG).show();
				//Toast.makeText(averseAlertActivity.this, Lat.toString(), Toast.LENGTH_LONG).show();
				Intent mapIntent = new Intent(endAverseActivity.this, mapActivity.class);
				mapIntent.putExtra("Method", 2);
				mapIntent.putExtra("DestinationLon", Lon);
				mapIntent.putExtra("DestinationLat", Lat);
	        	startActivity(mapIntent);
				//finish();
				
			}
		})
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
		
		
		
	
		
		super.onCreate(savedInstanceState);
	}

}
