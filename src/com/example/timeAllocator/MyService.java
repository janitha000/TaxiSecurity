package com.example.timeAllocator;

//Background service.............

import com.example.taxisecurity.MainActivity;
import com.example.taxisecurity.R;
import com.example.taxisecurity.R.drawable;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class MyService extends Service {
	private final static String TAG = "MyService";
	private long totalTime;
	Notification noti; 
	NotificationManager nmgr; public static  int NOTIFICATION_ID = 2; 
	Uri notification ;
	Ringtone r;
    public static final String COUNTDOWN_BR = "com.example.taxisecurity.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);
    private Bundle time;
    private float Z_FORWARD_THRESHOLD = 0f;
    String second;
    String input;
    long seconds;
	 PendingIntent inputp;
    boolean ringcheck;
    boolean autostop=false;
    CountDownTimer cdt = null;
    CountDownTimer cdt2 = null;
    AlertDialog dialog;
	CountDownTimer countDownTimer;
    protected void onHandleIntent(Intent intent) {
        totalTime = intent.getLongExtra("test", 0);
        Log.i(TAG, "total time assign..");
        if (totalTime != 0) {
          	  Log.i(TAG, "total time 0...");
        }
    }

    @Override
    public void onDestroy() {
      
    	nmgr.cancel(2);
    	  cdt.cancel();
   		
        if(ringcheck==true){
              	r.stop();
        }
        Toast.makeText(MyService.this, "On destroy",
				Toast.LENGTH_LONG).show();
        
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {     
       final Vibrator v = (Vibrator) MyService.this.getSystemService(Context.VIBRATOR_SERVICE);

    	time = intent.getExtras(); // The problem area (Eclipse says this is unreachable code)
        totalTime = time.getLong("total");
        System.out.println(totalTime);
        Log.i(TAG, "Starting timer onstrtcmd...");
        nmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE); 
		noti = new Notification(R.drawable.ic_launcher,"Time Allocator is Set", System.currentTimeMillis()); 
		
		Intent intent1 = new Intent(MyService.this,StopTimerActivity.class); 
		intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		final PendingIntent pIntent = PendingIntent.getActivity(MyService.this, 0, intent1,0); 
		
		Intent intent2=new Intent(MyService.this,timeAllocatorActivity.class);
		intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		final PendingIntent pIntent2 = PendingIntent.getActivity(MyService.this, 0, intent2,0); 
		
		noti.flags |= Notification.FLAG_ONGOING_EVENT;
		noti.flags |= Notification.FLAG_AUTO_CANCEL;
		noti.defaults |= Notification.DEFAULT_LIGHTS;
		noti.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
		notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
  	    r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        cdt = new CountDownTimer(totalTime, 1000) {
        @Override
	    public void onTick( long millisUntilFinished) {
        	Log.i(TAG, "countdown seconds oncreate");
	        System.out.println(millisUntilFinished);
	        seconds=millisUntilFinished/1000;
	        second=String.format("%02d", seconds / (60*60))
	           			+ ":" + String.format("%02d", (seconds % (60*60))/60)	
	           			+ ":" + String.format("%02d", seconds % 60);
	        if (seconds < 50  ) {
            input="Extend Time\n"+second;
		        if(!r.isPlaying()){
		         ringcheck=true;
		         r.play();
		           							
		        }
				v.vibrate(500);
				inputp=pIntent2;
				
				if(seconds<30){
					ringcheck=false;
					if(r.isPlaying()){
						v.cancel();
						r.stop();
					}
					if(seconds<2){
						autostop=true;
					}
				}
			}	   
		else{
			NOTIFICATION_ID=2;
			input=second;
			inputp=pIntent;
			if(r.isPlaying()){
				ringcheck=false;
				r.stop();
			}
		}
	    noti.setLatestEventInfo(MyService.this,"Count Down Timer", input, inputp);
	    nmgr.notify(NOTIFICATION_ID,noti);
	    bi.putExtra("countdown", millisUntilFinished);
	    sendBroadcast(bi);
     }
        @Override
        public void onFinish() {
        	Toast.makeText(MyService.this, "call alert dialog",
    					Toast.LENGTH_LONG).show();
	   	  	v.vibrate(500);
	   	  	alertdialog();
	   	  	Log.i(TAG, "Timer finished");
        }
        };
        cdt.start();
       return START_STICKY;    
   }
    @Override
    public IBinder onBind(Intent arg0) {       
        return null;
    }
	public void alertdialog(){
    	final Vibrator v1 = (Vibrator) MyService.this.getSystemService(Context.VIBRATOR_SERVICE);

		countDownTimer = new CountDownTimer(20000, 1000) {//Wait for 20 seconds for the user to verify himself.
			public void onTick(long millisUntilFinished) {
				v1.vibrate(500);
				System.out.println("seconds remaining: " + millisUntilFinished / 1000);
		        //.....................
				//Intent sms=new Intent(TimerNotificationActivity.this,smsService.class);
	          	//startService(sms);.........................//
	        }
		     public void onFinish() {
		    	 v1.cancel();
		        dialog.cancel();
	        	Intent intent = new Intent(MyService.this, MainActivity.class);//......................Main activity of the entire app
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
				//.................
				//Intent sms=new Intent(TimerNotificationActivity.this,smsService.class);
	      		//startService(sms);
				startActivity(intent);
		        //finish();
	        	Toast.makeText(MyService.this, "No reply..ooooppppppppzzzzzzzzzzz...",
							Toast.LENGTH_LONG).show();
	        	//sms service............................
	        }
		};
		countDownTimer.start();
		Toast.makeText(MyService.this, "alert dialog",
				Toast.LENGTH_LONG).show();
		final AlertDialog.Builder alertinitial = new AlertDialog.Builder(this);
		dialog = alertinitial.create();

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
    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    dialog.show();
    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
	{            
	      @Override
	      public void onClick(View v)
	      {
	    	 // countDownTimer.cancel();
	    	  String value = input.getText().toString();
	      	  if(!value.equals("abcd")){
	      		//................
	      		//Intent sms=new Intent(TimerNotificationActivity.this,smsService.class);
	      		//startService(sms);..............................//
        		input.setText("");
	      		input.setHint("password");
	      		Toast.makeText(MyService.this, "Wrong Password.Enter correct one...",
						Toast.LENGTH_LONG).show();
	      	  }
	      	  else{
	      		Intent stop;
	      		dialog.dismiss();
	      		dialog.cancel();
	      		countDownTimer.cancel();
	      		//.....................
	      		//stop =new Intent(Stopper.this,smsService.class);
	      		//stopService(stop);..................//
	      		Intent intent = new Intent(MyService.this, MainActivity.class);//.............Main activity of the entire app
	    		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
              //  finish();
				Toast.makeText(MyService.this, "The End!!",
						Toast.LENGTH_LONG).show();
	      	  }
	      }
	  });
	 
	}
}