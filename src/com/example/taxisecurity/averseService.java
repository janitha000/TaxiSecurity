package com.example.taxisecurity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class averseService extends Service implements LocationListener {
Double Lat;
Double Lon;
Double lat;

public float preDis=999999999;
Timer timerAverse;
public int counter=0;
public float prevRightdis=0;


Double latitude=0.0;
Double longitude=0.0;

float disChanged=0;
boolean Notignore = true;



class runServiceclass extends TimerTask {
    @Override
    public void run() {
        hSendSMS.sendEmptyMessage(0);
    }
};

Handler hSendSMS = new Handler() {
	public void handleMessage(Message msg) {
		latitude = MyLocation.latitude;
		longitude = MyLocation.longitude;
		runService();
        
	};
};


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		Lat = intent.getDoubleExtra("Latitiude", 0);
//		Lon = intent.getDoubleExtra("Longtitude", 0);
		
		 Lat = 7.351822;  //Destination coordinates
		 Lon = 80.615541;
		 
		 showRecordingNotification();
		 
		 try {
	            long intervalSendSMS = 30*1000;
	            timerAverse = new Timer();
	            timerAverse.schedule(new runServiceclass(), 0, intervalSendSMS);

	        } catch (NumberFormatException e) {
	            Toast.makeText(this, "error running service: " + e.getMessage(),
	                    Toast.LENGTH_SHORT).show();
	        } catch (Exception e) {
	            Toast.makeText(this, "error running service: " + e.getMessage(),
	                    Toast.LENGTH_SHORT).show();
	        }
		
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
		
		Toast.makeText(averseService.this, "Service Called", Toast.LENGTH_LONG).show();

		Toast.makeText(averseService.this, latitude.toString() +" "+ longitude.toString(), Toast.LENGTH_LONG).show();
		float dis = getDistance(Lat, Lon, latitude, longitude);
		String formattedNumber = Float.toString(dis);
		Toast.makeText(averseService.this,formattedNumber, Toast.LENGTH_LONG).show();
		
		if(preDis < dis ){         //If the previous distance is getting smaller 
			disChanged = dis - preDis;
			if(disChanged>10){
				counter++;
			
				if (counter==1){		//mark the last distance
					prevRightdis=preDis;
				}
			}
		}
		if(dis < prevRightdis){     //if in the right direction make counter 0
			counter = 0;
			prevRightdis = 0;
		}
		
		if (counter==5){			//If counter == 5 then send the notification
			counter=0;
			Intent i = new Intent();
			i.setClass(this, averseAlertActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putExtra("DestinationLon", Lon);
			i.putExtra("DestinationLat", Lat);
			startActivity(i);
		}
		
		if(dis < 10 && Notignore){
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			
			 
		    alert.setTitle("Averse Destination Service")
		    .setMessage("You Arrived at your destination")
		    .setPositiveButton("Ignore", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		        	Notignore=true;
		        	
		        	
		        }
		     })
		    .setNegativeButton("End Averse Service", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	Intent stopIntent = new Intent(averseService.this, endAverseActivity.class);
		        	stopService(stopIntent);
		        	
		        }
		     })
		     
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
		}
		
		preDis = dis;
		
		Toast.makeText(averseService.this,"Counter is" +counter, Toast.LENGTH_LONG).show();
		
		
	}
	


	
		
	public float getDistance(Double deslan, Double deslon, Double curlan, Double curlon) {
		Location locationA = new Location("Destination");

		locationA.setLatitude(deslan);
		locationA.setLongitude(deslon);

		Location locationB = new Location("Current Location");

		locationB.setLatitude(curlan);
		locationB.setLongitude(curlon);

		float distance =  locationA.distanceTo(locationB);
	    return distance;
		
	
		
	}
	

	

	@Override
	public void onLocationChanged(Location location) {
		
		
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
	public void onDestroy() {
    	Toast.makeText(this, "onDestroy Called", Toast.LENGTH_LONG).show();
    	
    	timerAverse.cancel();
        timerAverse.purge();
        
    	
        Toast.makeText(this, "Alarm destroyed ...", Toast.LENGTH_LONG).show();
          super.onDestroy();
          Toast.makeText(this, "Service destroyed ...", Toast.LENGTH_LONG).show();
    }

}
