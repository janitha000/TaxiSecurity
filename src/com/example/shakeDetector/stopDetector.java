package com.example.shakeDetector;

import java.lang.reflect.Field;

import com.example.taxisecurity.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

public class stopDetector extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
	    alert
		.setTitle("Stop Shake Detector")
		.setMessage("This will stop the shake detector")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setCancelable(true)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
          		dialog.cancel();
          		Intent intent = new Intent(stopDetector.this, MainActivity.class);//..............Goes to the main screen of the application...//
          		Intent service = new Intent(stopDetector.this,Detector.class);
           		stopService(service);
                startActivity(intent);
                finish();
  				Toast.makeText(stopDetector.this, "The End of Detector!!",
  						Toast.LENGTH_LONG).show();
			}
		})
		.setNegativeButton("Resume",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
          		dialog.dismiss();
          		dialog.cancel();
          		Intent intent = new Intent(stopDetector.this, MainActivity.class);//..............Goes to the main screen of the application...//
  				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);	      				
                startActivity(intent);
                Toast.makeText(stopDetector.this, "Resumed..!!",
  						Toast.LENGTH_LONG).show();
			}
		});alert.show();
	}

}
