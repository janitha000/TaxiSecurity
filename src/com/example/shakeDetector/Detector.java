package com.example.shakeDetector;

//Background service.............


import com.example.sms.smsService;
import com.example.taxisecurity.R;
import com.example.taxisecurity.R.drawable;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

public class Detector extends Service implements SensorEventListener {
	private final static String TAG = "Detector";
	private long totalTime;
	public NotificationManager notificationManager;
    Sensor accelerometer;
    SensorManager sm;
   // TextView acceleration;
 
    
    protected void onHandleIntent(Intent intent) {
        totalTime = intent.getLongExtra("test", 0);
        Log.i(TAG, "total time assign..");
        if (totalTime != 0) {
          	  Log.i(TAG, "total time 0...");
        }
    }
    
    @Override
    public void onDestroy() {
        Log.i(TAG, "Detector Cancelled");
        notificationManager.cancel(1);
    	Toast.makeText(Detector.this, "Detector Stopped",
    			Toast.LENGTH_LONG).show();
        sm.unregisterListener(this,this.accelerometer);
        super.onDestroy();
        
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {       
    	 notificationManager =
    		    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    		
    		CharSequence notiText = "Shake Detector Activated";
    		long meow = System.currentTimeMillis();

    		Notification notification = new Notification(R.drawable.ic_launcher, notiText, meow);

    		Context context = getApplicationContext();
    		CharSequence contentTitle = "Shake Detector";
    		CharSequence contentText = "Click to disable";
    		Intent notificationIntent = new Intent(Detector.this, stopDetector.class);
    		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
    		notification.flags |= Notification.FLAG_ONGOING_EVENT;
    		//notification.flags |= Notification.FLAG_AUTO_CANCEL;
    		notification.defaults |= Notification.DEFAULT_LIGHTS;
    		notificationManager.notify(1,notification);

    		
        // Your Code
        Toast.makeText(Detector.this, "Started Sensor",
    			Toast.LENGTH_LONG).show();
        
        this.sm = ((SensorManager)getSystemService("sensor"));
	    this.accelerometer = this.sm.getDefaultSensor(1);
	    this.sm.registerListener(this, this.accelerometer, 3);
      
       return 0;    
   }
   
	@Override
	public void onSensorChanged(SensorEvent paramSensorEvent) {
		// TODO Auto-generated method stub
		 float f = (float)Math.sqrt(Math.pow(paramSensorEvent.values[0], 2.0D) + Math.pow(paramSensorEvent.values[1], 2.0D) + Math.pow(paramSensorEvent.values[2], 2.0D));
		    if (f > 25.45862F)
		    {
		    	
		    	Toast.makeText(Detector.this, "Shake Shake Shake...",
		    			Toast.LENGTH_LONG).show();
		    	System.out.println("Shake Shake Shake Shake....................");
		    	
		    	Intent smsIntent = new Intent(Detector.this, smsService.class);
		    	startService(smsIntent);
		    	this.stopSelf();
		      //return;
		    }
		   
		
	}
	
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
}