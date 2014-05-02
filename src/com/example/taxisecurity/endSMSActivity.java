package com.example.taxisecurity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class endSMSActivity extends Activity {
	
	String password = "janitha";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this); 
		 alert.setView(input);
		 
	    alert.setTitle("Ending send SMS")
	    .setMessage("To end sending SMS type your password and press OK")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	String value = input.getText().toString();
	        	//password eka SQLite walin aragena compare karanna one
	        	if(value.equals(password)){
	        	finish(); //Stops the transparent Activity (Do not remove)
	        	Intent stopIntent = new Intent(endSMSActivity.this, smsService.class);
//	        	stopIntent.putExtra("stop", "true");
	        	stopService(stopIntent); //stops the smsService
	        	}
	        	else {
	         // re appear wenna hadanna
	        		//counter ekak daanna 3 wathakda balanna
	        		//reverse nm eth yawanna hadanna
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
