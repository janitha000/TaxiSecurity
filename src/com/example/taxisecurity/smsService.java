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
	BroadcastReceiver br;
	
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
          //setup();
          sendMultipleSMS();
          
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
    	
    	unregisterReceiver(alarmReceiver);
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
    
    public static final String ACTION_NAME = "com.helloworld.MYACTION";
    private IntentFilter myFilter = new IntentFilter(ACTION_NAME);
    
    public void sendMultipleSMS() {
    	registerReceiver(alarmReceiver, myFilter);
    	AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(ACTION_NAME);   
        int i=0;
        while(i<5){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i,
        intent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (5 * 1000), pendingIntent);
        i++;
        }
        
        //Toast.makeText(this, "Alarm set", Toast.LENGTH_LONG).show();
    	 
	}
    
    BroadcastReceiver alarmReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Alarm worked", Toast.LENGTH_LONG).show();          
        }
    };
    
    
	 //winadi 1n 1ta locations updates yanna puluwan widihata hadanna
    
//    private void setup() {
//        br = new BroadcastReceiver() {
//               @Override
//               public void onReceive(Context c, Intent i) {
//                      Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();
//                      }
//               };
//        registerReceiver(br, new IntentFilter("com.example.taxisecurity") );
//        pi = PendingIntent.getBroadcast( this, 0, new Intent("com.example.taxisecurity"),0 );
//        am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
//  }

    //alarmmanager stop ekath hadanna
    
    
    //sms service destroy ekath hadanna
    
    
}
