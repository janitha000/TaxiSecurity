package com.example.taxisecurity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.widget.Toast;

public class smsService extends Service  {
	SmsManager smsManager;
	AlarmManager am;
	PendingIntent pi;
	
	String phoneNo = "0716544588";
	String sms = "Janitha";
	

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
          setup();
          
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//sendOneSMS(phoneNo,sms);
		sendMultipleSMS();
        showRecordingNotification();
		return super.onStartCommand(intent, flags, startId);
	}
    
    @Override
    public void onDestroy() {
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
    
    public void sendMultipleSMS() {
    	am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 
    			1000 * 5, pi );
    	Toast.makeText(this, "Alarm Set", Toast.LENGTH_LONG).show();
    	 
	}
    
    
	 //winadi 1n 1ta locations updates yanna puluwan widihata hadanna
    
    private void setup() {
        BroadcastReceiver br = new BroadcastReceiver() {
               @Override
               public void onReceive(Context c, Intent i) {
                      Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();
                      }
               };
        registerReceiver(br, new IntentFilter("com.example.taxisecurity") );
        pi = PendingIntent.getBroadcast( this, 0, new Intent("com.example.taxisecurity"),0 );
        am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
  }

    //alarmmanager stop ekath hadanna
    
    //sms service destroy ekath hadanna
}