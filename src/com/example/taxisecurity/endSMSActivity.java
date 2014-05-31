package com.example.taxisecurity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class endSMSActivity extends Activity {
	String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Get the Password from the sharedpreferences
		SharedPreferences storage = getSharedPreferences("ContactData",Context.MODE_PRIVATE );
		password = storage.getString("password", "Not Working");
		
		String rev1Pwd = new StringBuilder(password).reverse().toString(); //Reverse password
		
		super.onCreate(savedInstanceState);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this); 
		 alert.setView(input);
		 
	    alert.setTitle("Ending send SMS")
	    .setMessage("To end sending SMS type your password and press OK")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	String value = input.getText().toString();
	        	if(value.equals(password)){
		        finish(); //Stops the transparent Activity (Do not remove)
	        	Intent stopIntent = new Intent(endSMSActivity.this, smsService.class);
	        	stopService(stopIntent); //stops the smsService
	        	}
	        	else {
	        		String revPwd = new StringBuilder(password).reverse().toString();
	        		if(revPwd == value){ //If the password was typed reverse
	        			Intent stopIntent = new Intent(endSMSActivity.this, averseService.class);
	    	        	stopService(stopIntent); //stops the averseService
	        			startService(new Intent(endSMSActivity.this,smsService.class));
	        		}
	        	}
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	finish();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
		
		
		
	}
	
}
