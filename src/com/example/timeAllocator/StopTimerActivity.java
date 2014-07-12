package com.example.timeAllocator;

import com.example.taxisecurity.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewDebug.FlagToString;
import android.widget.EditText;
import android.widget.Toast;

public class StopTimerActivity extends Activity {
	String password;
	AlertDialog  dialog1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		SharedPreferences storage = getSharedPreferences("ContactData",Context.MODE_PRIVATE );
		password = storage.getString("password", "Not Working");
		String rev1Pwd = new StringBuilder(password).reverse().toString();
		alertdialog();
		
		
	}
	public void alertdialog(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
	    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
	    input.setHint("password");
	    input.setSingleLine();
	    alert
		.setTitle("Stop Notification")
		.setMessage("Stop the timer???")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setView(input)
		.setCancelable(false)
		.setPositiveButton("Stop", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
	.setNegativeButton("Resume", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Intent intent= new Intent(StopTimerActivity.this, timeAllocatorActivity.class);//...........Not the main activity of the entire application.But to the count down timer class
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
	    dialog1 = alert.create();
	    dialog1.show();
	    dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
	      {            
	          @Override
	          public void onClick(View v)
	          {
	        	  String value = input.getText().toString();
	          	if(!value.equals(password)){
	          		//...............Intent sms=new Intent(TimerNotificationActivity.this,smsService.class);
	          		//startService(sms);................//
	        		input.setText("");
	          		input.setHint("password");	              		
	          		Toast.makeText(StopTimerActivity.this, "Wrong Password.Enter correct one...",
	  						Toast.LENGTH_LONG).show();
	  	     	}
	          	else{
	          		Intent stop;
	          		dialog1.dismiss();
	          		dialog1.cancel();
	          		Intent intent = new Intent(StopTimerActivity.this, MainActivity.class);//..............Goes to the main screen of the application...//
	          		Intent service = new Intent(StopTimerActivity.this,MyService.class);
	          		stopService(service);
	  				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);	      				
	                startActivity(intent);
	                finish();
	  				Toast.makeText(StopTimerActivity.this, "The End!!",
	  						Toast.LENGTH_LONG).show();
	  			
	          	} 
          	}
	    });
	}
}
