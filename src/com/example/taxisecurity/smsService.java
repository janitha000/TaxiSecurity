package com.example.taxisecurity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.example.taxisecurity.MyLocation.LocationResult;

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
	
	
	LocationManager mLocationManager;
	
	
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
    
    public void procSendSMS() {  //********* loc eka null da kiyala check karala balala yawanna
        try {
        	//sendOneSMS("0716544588",  "test sms");
        	//String loc =getLocation();

        	
        	Toast.makeText(this, SLocation, Toast.LENGTH_LONG).show();
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
          Toast.makeText(this, "getLocation called", Toast.LENGTH_LONG).show();
   	   Location location = null;
   	   
   	       
   	   mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
   	   
   	        // getting GPS status
   	        Boolean isGPSEnabled = mLocationManager
   	                .isProviderEnabled(LocationManager.GPS_PROVIDER);
   	        //Toast.makeText(this, isGPSEnabled.toString(), Toast.LENGTH_LONG).show();

   	        // getting network status
   	        Boolean isNetworkEnabled = mLocationManager
   	                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

   	        if (!isGPSEnabled && !isNetworkEnabled) {
   	            // no network provider is enabled
   	        	
   	        } else {
   	            //this.canGetLocation = true;
   	            if (isGPSEnabled) {
   	            	
   	                if (location == null) {
   	                	
   	                    mLocationManager.requestLocationUpdates(
   	                            LocationManager.GPS_PROVIDER,
   	                            1000,
   	                            0, this);
   	                    
   	                    //Log.d("GPS", "GPS Enabled");
   	                    if (mLocationManager != null) {
   	                    	
   	                        location = mLocationManager
   	                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
   	                        
   	                        	
   	                        if (location != null) {
   	                        	
   	                        	SLocation = "Latitude is " + location.getLatitude() + "Longitude is " + location.getLongitude();
   	                        	
   	                        }
   	                        else
   	                        {
   	                        	mLocationManager.requestLocationUpdates(
   	        	                        LocationManager.NETWORK_PROVIDER,
   	        	                        5*1000,
   	        	                        0,  this);
   	        	                //Log.d("Network", "Network Enabled");
   	        	                if (mLocationManager != null) {
   	        	                    location = mLocationManager
   	        	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
   	        	                    if (location != null) {
   	        	                    	SLocation = "Latitude is " + location.getLatitude() + "Longitude is " + location.getLongitude();
   	        	                    	Toast.makeText(this, "Network Location", Toast.LENGTH_LONG).show();
   	        	                    }
   	        	                } 
   	        	            
   	                        }
   	                    }
   	                }
   	            }
   	            // if GPS Enabled get lat/long using GPS Services
//   	            else if(isNetworkEnabled) {
//   	                mLocationManager.requestLocationUpdates(
//   	                        LocationManager.NETWORK_PROVIDER,
//   	                        5*1000,
//   	                        0,  this);
//   	                //Log.d("Network", "Network Enabled");
//   	                if (mLocationManager != null) {
//   	                    location = mLocationManager
//   	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//   	                    if (location != null) {
//   	                    	SLocation = "Latitude is " + location.getLatitude() + "Longitude is " + location.getLongitude();
//   	                    	Toast.makeText(this, "Network Location", Toast.LENGTH_LONG).show();
//   	                    }
//   	                } 
//   	            
//   	            
//   	                
//   	            }
   	        }
   	        //not sure 
   	        
   	        //Toast.makeText(this, SLocation, Toast.LENGTH_LONG).show();
   			
          
          
          
          
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		try {
            long intervalSendSMS = 20*1000;

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
    	
    	timerSendSMS.cancel();
        timerSendSMS.purge();
        mLocationManager.removeUpdates(this);
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
    
   public String getLocation(){
	   Toast.makeText(this, "getLocation called", Toast.LENGTH_LONG).show();
	   Location location = null;
	   
	       
	   mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	   
	        // getting GPS status
	        Boolean isGPSEnabled = mLocationManager
	                .isProviderEnabled(LocationManager.GPS_PROVIDER);
	        //Toast.makeText(this, isGPSEnabled.toString(), Toast.LENGTH_LONG).show();

	        // getting network status
	        Boolean isNetworkEnabled = mLocationManager
	                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

	        if (!isGPSEnabled && !isNetworkEnabled) {
	            // no network provider is enabled
	        	
	        } else  {
	            //this.canGetLocation = true;
	            if (isGPSEnabled) {
	            	
	                if (location == null) {
	                	
	                    mLocationManager.requestLocationUpdates(
	                            LocationManager.GPS_PROVIDER,
	                            1000,
	                            5, this);
	                    
	                    //Log.d("GPS", "GPS Enabled");
	                    if (mLocationManager != null) {
	                    	
	                        location = mLocationManager
	                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                        
	                        	
	                        if (location != null) {
	                        	
	                        	SLocation = "Latitude is " + location.getLatitude() + "Longitude is " + location.getLongitude();
	                        	
	                        }
	                        else
	                        {
	                        	mLocationManager.requestLocationUpdates(
	        	                        LocationManager.NETWORK_PROVIDER,
	        	                        5*1000,
	        	                        10,  this);
	        	                //Log.d("Network", "Network Enabled");
	        	                if (mLocationManager != null) {
	        	                    location = mLocationManager
	        	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	        	                    if (location != null) {
	        	                    	SLocation = "Latitude is " + location.getLatitude() + "Longitude is " + location.getLongitude();
	        	                    	Toast.makeText(this, "Network Location", Toast.LENGTH_LONG).show();
	        	                    }
	        	                } 
	        	            
	                        }
	                    }
	                }
	                } else  {
	                	mLocationManager.requestLocationUpdates(
    	                        LocationManager.NETWORK_PROVIDER,
    	                        5*1000,
    	                        10,  this);
    	                //Log.d("Network", "Network Enabled");
    	                if (mLocationManager != null) {
    	                    location = mLocationManager
    	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    	                    if (location != null) {
    	                    	SLocation = "Latitude is " + location.getLatitude() + "Longitude is " + location.getLongitude();
    	                    	Toast.makeText(this, "Network Location", Toast.LENGTH_LONG).show();
    	                    }
    	                } 
	                	
	                }
	            

	        }
	        
			return SLocation;
	   
       
//       Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//       if(location == null)
//    	   Toast.makeText(this, SLocation, Toast.LENGTH_LONG).show();
//       if(location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
//    	   
//       }
//       else {
//           mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
//           
//       }
//       
//       SLocation = "Latitude is " + location.getLatitude() + "Longitude is " + location.getLongitude();
//       mLocationManager.removeUpdates((LocationListener) this);
       
   }







@Override
public void onLocationChanged(Location location) {
	Toast.makeText(this, "OnLocationChanged", Toast.LENGTH_LONG).show();
	SLocation = "Latitude is " + location.getLatitude() + "Longitude is " + location.getLongitude();
	
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
