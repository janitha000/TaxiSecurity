package com.example.taxisecurity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class averseService extends Service {
Double Lat;
Double Lon;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		 Lat = 7.324858;
		 Lon =  80.625870;
		showRecordingNotification();
		runService();
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void showRecordingNotification(){
    	NotificationManager notificationManager =
    		    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    		int icon = R.drawable.common_signin_btn_text_normal_light;
    		CharSequence notiText = "Your notification from the service";
    		long meow = System.currentTimeMillis();

    		Notification notification = new Notification(icon, notiText, meow);

    		Context context = getApplicationContext();
    		CharSequence contentTitle = "Averse DEstination Service";
    		CharSequence contentText = "Averse DEstination service is Runnig";
    		Intent notificationIntent = new Intent(this, endAverseActivity.class);
    		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

    		int SERVER_DATA_RECEIVED = 1;
    		notificationManager.notify(SERVER_DATA_RECEIVED, notification);
    }
	
	public void runService(){
		
		
		
		Intent i = new Intent();
		i.setClass(this, averseAlertActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.putExtra("DestinationLon", Lon);
		i.putExtra("DestinationLat", Lat);
		startActivity(i);
		
		
	}

}
