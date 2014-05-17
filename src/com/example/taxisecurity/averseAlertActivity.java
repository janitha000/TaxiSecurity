package com.example.taxisecurity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class averseAlertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		createDialog();
		
		
		
	
		
		super.onCreate(savedInstanceState);
	}
	
	public void createDialog(){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		 
	    alert.setTitle("Averse Destination Service")
	    .setMessage("WARNING  To stop click on the notification")
	    .setPositiveButton("Ignore", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	
	        	//Ignore and continue
	        finish();	
	        }
	     })
	    .setNegativeButton("Send SMS", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	Intent stopIntent = new Intent(averseAlertActivity.this, averseService.class);
	        	stopService(stopIntent);
	        	Intent serviceIntent = new Intent(averseAlertActivity.this, smsService.class);
	        	startService(serviceIntent);
	        	finish();
	        }
	     })
	     .setNeutralButton("Show In Map", new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				double Deafout=0.0;
				
				Intent gIntent = getIntent();
				Double Lat = gIntent.getDoubleExtra("DestinationLat",Deafout);
				Double Lon = gIntent.getDoubleExtra("DestinationLon",Deafout);
				
				//Toast.makeText(averseAlertActivity.this, Lat.toString(), Toast.LENGTH_LONG).show();
				Intent mapIntent = new Intent(averseAlertActivity.this, mapActivity.class);
				mapIntent.putExtra("Method", 2);
				mapIntent.putExtra("DestinationLon", Lon);
				mapIntent.putExtra("DestinationLat", Lat);
	        	startActivity(mapIntent);
				//finish();
				
			}
		})
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	@Override
	protected void onResume() {
		createDialog();
		super.onResume();
	}
	
}
