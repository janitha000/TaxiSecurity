package com.example.taxisecurity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.widget.Toast;

public class smsService extends Service  {
	SmsManager smsManager;
	AlarmManager alarmManager;
	PendingIntent pendingIntent;
	
	Boolean stop = false;
	String phoneNo = "0716544588";
	String sms = "Janitha";
	
	Timer timerSendSMS = new Timer();

    class taskSendSMS extends TimerTask {
        @Override
        public void run() {
            hSendSMS.sendEmptyMessage(0);
        }
    };
    
    Handler hSendSMS = new Handler() {
    	public void handleMessage(Message msg) {
    		procSendSMS();
            
    	};
    };
    
    public void procSendSMS() {
        try {
        	Toast.makeText(this, "Alarm set", Toast.LENGTH_LONG).show();

        } catch (Exception e) {

        }
    }
    
    

    
	
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public void onCreate() {
          super.onCreate();
          Toast.makeText(this,"Service created ...", Toast.LENGTH_LONG).show();
          smsManager = SmsManager.getDefault();
          //setup();
          //sendMultipleSMS();
          
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//sendOneSMS(phoneNo,sms);
		//sendMultipleSMS();
		try {
            long intervalSendSMS = 10*1000;

            timerSendSMS = new Timer();

            timerSendSMS.schedule(new taskSendSMS(), 0, intervalSendSMS);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "error running service: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "error running service: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        showRecordingNotification();
		return super.onStartCommand(intent, flags, startId);
	}
    
    @Override
    public void onDestroy() {
    	Toast.makeText(this, "onDestroy Called", Toast.LENGTH_LONG).show();
    	stop=true;
    	timerSendSMS.cancel();
        timerSendSMS.purge();
    	//alarmManager.cancel(pendingIntent);
    	//unregisterReceiver(alarmReceiver);
        Toast.makeText(this, "Alarm destroyed ...", Toast.LENGTH_LONG).show();
          super.onDestroy();
          Toast.makeText(this, "Service destroyed ...", Toast.LENGTH_LONG).show();
    }
    
    private void showRecordingNotification(){
    	NotificationManager notificationManager =
    		    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    		int icon = R.drawable.common_signin_btn_text_normal_light;
    		CharSequence notiText = "Your notification from the service";
    		long meow = System.currentTimeMillis();

    		Notification notification = new Notification(icon, notiText, meow);

    		Context context = getApplicationContext();
    		CharSequence contentTitle = "Send SMS Service";
    		CharSequence contentText = "Send SMS service is Runnig";
    		Intent notificationIntent = new Intent(this, endSMSActivity.class);
    		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

    		int SERVER_DATA_RECEIVED = 1;
    		notificationManager.notify(SERVER_DATA_RECEIVED, notification);
    }
    
    public void sendOneSMS(String phoneNo,  String sms){
    	
		try {
			
			smsManager.sendTextMessage(phoneNo, null, sms, null, null);
			Toast.makeText(getApplicationContext(), "SMS Sent!",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"SMS faild, please try again later!",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
    }
    
    public static final String ACTION_NAME = "com.example.taxisecurity.MYACTION";
    private IntentFilter myFilter = new IntentFilter(ACTION_NAME);
    
    public void sendMultipleSMS() {
    	
//    	registerReceiver(alarmReceiver, myFilter);
//    	alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent intent = new Intent(ACTION_NAME);   
//        int i=0;
//        while(i<5){
//        	
//        		pendingIntent = PendingIntent.getBroadcast(this, i,
//        				intent, PendingIntent.FLAG_ONE_SHOT);
//        		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (5 * 1000), pendingIntent);
//        		i++;
//        	
//        } //loop eken eliyata paninne naa :(
      
        //Toast.makeText(this, "Alarm set", Toast.LENGTH_LONG).show();
    	
    	
    	 
	}
    
    private boolean checkStop() {
    	
		return stop;
	}

//	BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context, "Alarm worked", Toast.LENGTH_LONG).show();          
//        }
//    };
    


    //alarmmanager stop ekath hadanna ?????
    
    
    
    
    
}
