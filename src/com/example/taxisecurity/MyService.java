package com.example.taxisecurity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	private final static String TAG = "MyService";
	private long totalTime;
    public static final String COUNTDOWN_BR = "com.example.taxisecurity.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);
    private Bundle time;
    private float Z_FORWARD_THRESHOLD = 0f;
    CountDownTimer cdt = null;
    protected void onHandleIntent(Intent intent) {
        totalTime = intent.getLongExtra("test", 0);
        Log.i(TAG, "total time assign..");
        if (totalTime != 0) {
          	  Log.i(TAG, "total time 0...");
        }
    }

    @Override
    public void onDestroy() {

        cdt.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {       
    	time = intent.getExtras(); // The problem area (Eclipse says this is unreachable code)
        totalTime = time.getLong("total");
        System.out.println(totalTime);
        Log.i(TAG, "Starting timer onstrtcmd...");
        cdt = new CountDownTimer(totalTime, 1000) {
	           @Override
	           public void onTick( long millisUntilFinished) {
	           	  Log.i(TAG, "countdown seconds oncreate");
	           	  System.out.println(millisUntilFinished);
	           	  bi.putExtra("countdown", millisUntilFinished);
	           	  sendBroadcast(bi);
               }
               @Override
               public void onFinish() {
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
}