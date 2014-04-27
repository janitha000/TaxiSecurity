package com.example.taxisecurity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class smsService extends Service {
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public void onCreate() {
          super.onCreate();
          Toast.makeText(this,"Service created ...", Toast.LENGTH_LONG).show();
          //sendSMS();
          showRecordingNotification();
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
    
    public void sendSMS(){
    	String phoneNo = "0716544588";
		String sms = "This is a test message";

		try {
			SmsManager smsManager = SmsManager.getDefault();
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
    
    
	 //winadi 1n 1ta locations updates yanna puluwan widihata hadanna

}
