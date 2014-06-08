package com.example.taxisecurity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class timeAllocatorActivity extends Activity  {

	private TextView tv,remaining;
	private Button buttonStart,buttonStop;
	private EditText edtHours, edtMinutes;
	
	private static final String TAG = "MainActivity";
	private long totalTime;
	public static final String TOTALTIME_BR = "com.example.MainActivity.ToTALTIME_BR";
	
	Intent ai = new Intent(TOTALTIME_BR);
	Intent dataIntent;
	Intent serviceintent;
	int flag=2;
	int extend=2;
	long milliseconds;
	
	Notification noti; 
	NotificationManager nmgr; public static final int NOTIFICATION_ID = 0; 
	
	AlertDialog.Builder alert;
	AlertDialog dialog ;
	String val="abcd";


	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    
	    edtMinutes = (EditText) findViewById(R.id.edtMinutes);
		edtHours = (EditText) findViewById(R.id.edtHours);
		tv=(TextView)findViewById(R.id.displayTime); 
		remaining=(TextView)findViewById(R.id.text);	
		buttonStart = (Button)findViewById(R.id.btnStart);
		buttonStop = (Button)findViewById(R.id.btnStop);
		nmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE); 
		noti = new Notification(R.drawable.ic_launcher,"Time Allocator is Set", System.currentTimeMillis()); 
		Intent intent = new Intent(timeAllocatorActivity.this,StopTimerActivity.class); 
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pIntent = PendingIntent.getActivity(timeAllocatorActivity.this, 0, intent, 0); 
		noti.setLatestEventInfo(timeAllocatorActivity.this, "Event Header","Time Allocator is Set", pIntent);
		noti.flags |= Notification.FLAG_ONGOING_EVENT;
	    buttonStart.setOnClickListener(new OnClickListener() {
	    		
	    	@Override
			public void onClick(View v) {
				if (edtHours.getText().toString().equals("") && edtMinutes.getText().toString().equals("")){
					Toast.makeText(timeAllocatorActivity.this, "Please Enter Travel Time...",
							Toast.LENGTH_LONG).show();			
					
				}
				else{
					nmgr.notify(NOTIFICATION_ID,noti);
					setTimer();
				}
				
			}
		});
	    
	    buttonStop.setOnClickListener(new OnClickListener() {
	    	
	    	@Override
	    	public void onClick(View v) {
	  	   		Intent stop=new Intent(timeAllocatorActivity.this, StopTimerActivity.class);
	    		startActivity(stop);
	    	}
	    });

	}
	
	private void setTimer() {
		int hours = 0;
		int minutes=0;
		int time= 0;
		if (edtHours.getText().toString().equals("") && edtMinutes.getText().toString().equals("")){
			Toast.makeText(timeAllocatorActivity.this, "Please Enter Travel Time...",
					Toast.LENGTH_LONG).show();			
		}
		else{
			edtMinutes.setVisibility(View.GONE);
			edtHours.setVisibility(View.GONE);
			buttonStart.setVisibility(View.GONE);
			buttonStop.setVisibility(View.VISIBLE);
			remaining.setVisibility(View.VISIBLE);
			tv.setVisibility(View.VISIBLE);		
			
			if (!edtHours.getText().toString().equals("") && !edtMinutes.getText().toString().equals("") ) {
				minutes = Integer.parseInt(edtMinutes.getText().toString());
				hours = Integer.parseInt(edtHours.getText().toString());
				time =(60*hours) + minutes;
			}else if(edtHours.getText().toString().equals("") && !edtMinutes.getText().toString().equals("")){
				minutes = Integer.parseInt(edtMinutes.getText().toString());
				time=minutes;
			}else if(!edtHours.getText().toString().equals("") && edtMinutes.getText().toString().equals("")){
				hours = Integer.parseInt(edtHours.getText().toString());
				time=60*hours;
			} 
			totalTime = 60 * time * 1000;
			dataIntent = new Intent(timeAllocatorActivity.this, MyService.class);
	        dataIntent.setClass(timeAllocatorActivity.this, MyService.class);
	        dataIntent.putExtra("total", totalTime);
	        
	        serviceintent = new Intent(timeAllocatorActivity.this,MyService.class).putExtra("total", totalTime);
			startService(serviceintent);
			Toast.makeText(timeAllocatorActivity.this, "Start Service from main",
					Toast.LENGTH_LONG).show();
					
		}
		char c=(char) minutes;
		
	}
    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {            
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };

    @Override  
    public void onResume() {
        super.onResume();        
        registerReceiver(br, new IntentFilter(MyService.COUNTDOWN_BR));
        Log.i(TAG, "Registered broacast receiver");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
        Log.i(TAG, "Unregistered broacast receiver");
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        tv.setText("");
        tv.setText("Time's up......!!!!!!!");
        super.onStop();
    }
   
    @Override
    public void onDestroy() {        
        stopService(new Intent(this, MyService.class));
        Log.i(TAG, "Stopped service");
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
        	 Log.i(TAG, "Start main");
        	
             milliseconds = intent.getLongExtra("countdown", 0);
             long seconds=milliseconds/1000;
             System.out.println(seconds);
             System.out.println(flag);

             tv.setText(String.format("%02d", seconds / (60*60))
					+ ":" + String.format("%02d", (seconds % (60*60))/60)	
					+ ":" + String.format("%02d", seconds % 60));  
        	if (milliseconds < 50*1000) {
        		//Extend timer notification
        		nmgr.cancelAll();
        		if(flag==2 && milliseconds < 50*1000){
        			 flag=1;
        			 System.out.println("alert dialg");
        			 alertdialog();
        			 System.out.println("flag value  "+flag);
        			 Log.i(TAG, "after ......");
			
        	}
		}
	      	if (milliseconds <5000) {
	      		//Clear existing notifications and display final verfication alert
	           		Intent stopacivity;
	        		Intent stopactivity = new Intent(timeAllocatorActivity.this, Stopper.class);
	        		stopactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(stopactivity);    
	        }
        }
    }
    
	private void alertdialog( ) {
			alert = new AlertDialog.Builder(this);
			final EditText input = new EditText(this);
			
		    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		    input.setHint("password");
		    input.setSingleLine();
	
		    alert
			.setTitle("Extend / Stop")
			.setMessage("Enter the password to proceed ")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setView(input)
			.setCancelable(false)
			.setNegativeButton("Extend Time", new DialogInterface.OnClickListener() {							
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
				}
			})
	
			.setPositiveButton("Stop", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				
					
				}
			});
		     dialog = alert.create();
	    	 dialog.show();
			 dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener()
	         {            
	             @Override
	             public void onClick(View v)
	             {
	           	 
	           	  String value = input.getText().toString();
	             	if(!value.equals(val)){
	             		System.out.println(value+" "+val);
	             		//.............................
	             		//Intent sms=new Intent(TimerNotificationActivity.this,smsService.class);
	             		//startService(sms);.................................//
	             		input.setText("");
	             		input.setHint("password");
	             		
	             		Toast.makeText(timeAllocatorActivity.this, "Wrong Password.Enter correct one...",
	     						Toast.LENGTH_LONG).show();
	             	}
	             		else{
	             			dialog.dismiss();
	             			dialog.cancel();
	             			Intent stop;
			        		dialog.cancel();
			        		//....................................
			        		//Intent stop =new Intent(TimerNotificationActivity.this,smsService.class);
			        		//stopService(stop);.................................//
	             			extender();
	             		}
	             		
	             	}});
		  
		 	dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
	 		{            
	              @Override
              	public void onClick(View v)
              	{
	            	 
	            	  String value = input.getText().toString();
	              	if(!value.equals(val)){
	              		
	              		//..........................
	              		//Intent sms=new Intent(TimerNotificationActivity.this,smsService.class);
	              		//startService(sms);..................................................//
	              		
	              		input.setText("");
	              		input.setHint("password");		              		
	              		Toast.makeText(timeAllocatorActivity.this, "Wrong Password.Enter correct one...",
	      						Toast.LENGTH_LONG).show();		              	              		
	              	}
	              	else{
	              		Intent stop;
	              		dialog.dismiss();
	              		dialog.cancel();
	              		//.............................................................
	              		// stop =new Intent(Stopper.this,smsService.class);
	              		//stopService(stop);.......................................................//
	              		Intent intent = new Intent(timeAllocatorActivity.this, MainActivity.class);
	              		Intent service = new Intent(timeAllocatorActivity.this,MyService.class);
	              		stopService(service);
	      				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
  		                startActivity(intent);
  		                finish();
	      				Toast.makeText(timeAllocatorActivity.this, "The End!!",
	      						Toast.LENGTH_LONG).show();
	      			    
	              	}
               }
		  });
	
	}
	
	void extender(){
		AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
	    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_NORMAL);
	    input.setHint("enter time");
	    input.setSingleLine();
	  	
	    alert1
		.setTitle("Extend time")
		.setMessage("Minutes to extend ???")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setView(input)
		.setCancelable(false)
		.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dlg, int which) {
				String value = input.getText().toString();
				dlg.dismiss();
				int extendMinutes = Integer.parseInt(input.getText().toString());									
				totalTime = milliseconds+(60 *extendMinutes* 1000);
				stopService(serviceintent);
				
				 dataIntent = new Intent(timeAllocatorActivity.this, MyService.class);
			     dataIntent.setClass(timeAllocatorActivity.this, MyService.class);
			     dataIntent.putExtra("total", totalTime);
			     serviceintent = new Intent(timeAllocatorActivity.this,MyService.class).putExtra("total", totalTime);
			     startService(serviceintent);
				 Toast.makeText(timeAllocatorActivity.this, "Start Service again from main",
							Toast.LENGTH_LONG).show();
 				 System.out.println("flag value after click  "+flag);
 				 flag=2;
				
			}
		}).show();
	}

	

	
}