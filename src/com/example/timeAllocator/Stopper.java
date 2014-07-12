package com.example.timeAllocator;
//whole class is not needed.........
import com.example.sms.smsService;
import com.example.taxisecurity.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.view.ViewDebug.FlagToString;
import android.widget.EditText;
import android.widget.Toast;

public class Stopper extends Activity {
	String password;
	CountDownTimer countDownTimer;
	AlertDialog dialog ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		System.out.println("Stopperrr");
		SharedPreferences storage = getSharedPreferences("ContactData",Context.MODE_PRIVATE );
		password = storage.getString("password", "Not Working");
		String rev1Pwd = new StringBuilder(password).reverse().toString();
		countDownTimer = new CountDownTimer(20000, 1000) {//Wait for 20 seconds for the user to verify himself.
		public void onTick(long millisUntilFinished) {
			System.out.println("seconds remaining: " + millisUntilFinished / 1000);
	        //.....................
			//Intent sms=new Intent(TimerNotificationActivity.this,smsService.class);
          	//startService(sms);.........................//
        }
	     public void onFinish() {
	        dialog.cancel();
        	Intent intent = new Intent(Stopper.this, MainActivity.class);//......................Main activity of the entire app
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
			startService(new Intent(Stopper.this,smsService.class));
	        finish();
        	Toast.makeText(Stopper.this, "No reply..ooooppppppppzzzzzzzzzzz...",
						Toast.LENGTH_LONG).show();
        }
	};
	countDownTimer.start();
		alertdialog();
		
	}
	public void alertdialog(){
		final AlertDialog.Builder alertinitial = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
  	    input.setHint("password");
	    input.setSingleLine();
	    
	    alertinitial
		.setTitle("Stop Notification")
		.setMessage("Time's up..!!! Are you safe?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setView(input)
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				
			}
	});
    dialog = alertinitial.create();
    dialog.show();
    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
	{            
	      @Override
	      public void onClick(View v)
	      {
	    	  countDownTimer.cancel();
	    	  String value = input.getText().toString();
	      	  if(!value.equals(password)){
	      		//................
	      		//Intent sms=new Intent(TimerNotificationActivity.this,smsService.class);
	      		//startService(sms);..............................//
        		input.setText("");
	      		input.setHint("password");
	      		Toast.makeText(Stopper.this, "Wrong Password.Enter correct one...",
						Toast.LENGTH_LONG).show();
	      	  }
	      	  else{
	      		Intent stop;
	      		dialog.dismiss();
	      		dialog.cancel();
	      		//.....................
	      		//stop =new Intent(Stopper.this,smsService.class);
	      		//stopService(stop);..................//
	      		Intent intent = new Intent(Stopper.this, MainActivity.class);//.............Main activity of the entire app
	      		Intent service = new Intent(Stopper.this,MyService.class);
	      		stopService(service);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
				Toast.makeText(Stopper.this, "The End!!",
						Toast.LENGTH_LONG).show();
	      	  }
	      }
	  });
	}
}
