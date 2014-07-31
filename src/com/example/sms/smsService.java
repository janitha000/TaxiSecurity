package com.example.sms;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;







import com.example.location.MyLocation;
import com.example.taxisecurity.R;
import com.example.taxisecurity.R.drawable;

import android.R.string;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.widget.Toast;

public class smsService extends Service implements LocationListener  {
	SmsManager smsManager;
	AlarmManager alarmManager;
	PendingIntent pendingIntent;
	String SLocation ="Not Working";
	
	String contact1Name;
	String phoneNo1 ;
	String contact2Name;
	String phoneNo2;
	
	
	//Get chosen Contact Details
	
	
	String sms;
	
	Timer timerSendSMS = new Timer();
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public void onCreate() {
          super.onCreate();
          //Toast.makeText(this,"Service created ...", Toast.LENGTH_LONG).show();
          smsManager = SmsManager.getDefault();
          //Toast.makeText(this, "getLocation called", Toast.LENGTH_LONG).show();
     
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SharedPreferences storage = getSharedPreferences("ContactData",0 );
		contact1Name = storage.getString("chosen1Name", "Contact Name");
		phoneNo1 = storage.getString("chosen1No", "111");
		contact2Name = storage.getString("chosen2Name", "Contact Name");
		phoneNo2 = storage.getString("chosen2No", "111");
		
		try {
            long intervalSendSMS = 60*1000; //*************change the time

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
        	sms = "I am at danger. My last known location is Latitude: "+ MyLocation.latitude
        			+ " Longitude: "+ MyLocation.longitude;
        	sendOneSMS(phoneNo1,  sms);
        	sendOneSMS(phoneNo2,  sms);
        	

        	SLocation = "Latitude is " + MyLocation.latitude  + "Longitude is " + MyLocation.longitude;
        	//Toast.makeText(this, SLocation, Toast.LENGTH_LONG).show();
        } catch (Exception e) {

        }
    }
    
    
    @Override
    public void onDestroy() {
    	//Toast.makeText(this, "onDestroy Called", Toast.LENGTH_LONG).show();
    	
    	timerSendSMS.cancel();
        timerSendSMS.purge();
        
        //Toast.makeText(this, "Alarm destroyed ...", Toast.LENGTH_LONG).show();
          super.onDestroy();
          Toast.makeText(this, "Service destroyed ...", Toast.LENGTH_LONG).show();
    }
    
    private void showRecordingNotification(){
    	NotificationManager notificationManager =
    		    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    		int icon = android.R.drawable.ic_dialog_alert;
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
    
 
@Override
public void onLocationChanged(Location location) {
	//Toast.makeText(this, "OnLocationChanged", Toast.LENGTH_LONG).show();
	
	
}







@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	// TODO Auto-generated method stub
	
}







@Override
public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
	
}







@Override
public void onProviderDisabled(String provider) {
	// TODO Auto-generated method stub
	
}
   

    
    
    
    
    
}
