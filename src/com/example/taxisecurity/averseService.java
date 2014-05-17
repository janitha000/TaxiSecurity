package com.example.taxisecurity;

import java.util.Timer;
import java.util.TimerTask;


import com.example.taxisecurity.smsService.taskSendSMS;

import android.R.string;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.FloatMath;
import android.util.Log;
import android.widget.Toast;

public class averseService extends Service implements LocationListener {
Double Lat;
Double Lon;
Double lat;
boolean isGPSEnabled = false;
public float preDis=999999999;
Timer timerAverse;
public int counter=0;
public float prevRightdis=0;

//flag for network status
boolean isNetworkEnabled = false;
LocationManager mLocationManager;
boolean canGetLocation = false;
LocationManager locationManager;
Location location;
Double latitude=0.0;
Double longitude=0.0;

//The minimum distance to change updates in metters
private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; //10 metters

//The minimum time beetwen updates in milliseconds
private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

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
		 Lat = 7.324858;   //Destination coordinates
		 Lon =  80.625870;
		 showRecordingNotification();
		 //getLocation();
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
		
		
		
//		LocationResult locationResult = new LocationResult(){
//    	    @Override
//    	    public void gotLocation(Location location){
//    	    	latitude=location.getLatitude();
//    	    	longitude=location.getLongitude();
//    	    	Log.i("MyActivity", latitude+" "+ longitude);   
//    	        
//    	     
//    	    }
//    	};
//    	
//    	MyLocation myLocation = new MyLocation();
//    	myLocation.getLocation(this, locationResult);
    	
    	
    	
		runService();
		//getLocation();
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
			counter++;
			
			if (counter==1){		//mark the last distance
				prevRightdis=preDis;
			}
		}
		if(dis < prevRightdis){     //if in the right direction make counter 0
			counter = 0;
			prevRightdis = 0;
		}
		
		if (counter==5){			//If counter == 5 then send the notification
			Intent i = new Intent();
			i.setClass(this, averseAlertActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putExtra("DestinationLon", Lon);
			i.putExtra("DestinationLat", Lat);
			startActivity(i);
		}
		
		preDis = dis;
		
		Toast.makeText(averseService.this,"Counter is" +counter, Toast.LENGTH_LONG).show();
		
		
		
		
		

		
		
	}
	
//	public Location getLocation()
//    {
//        try
//        {
//            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//            //getting GPS status
//            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            //getting network status
//            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//            if (!isGPSEnabled && !isNetworkEnabled)
//            {
//                // no network provider is enabled
//            }
//            else
//            {
//                this.canGetLocation = true;
//
//                //First get location from Network Provider
//                if (isNetworkEnabled)
//                {
//                    locationManager.requestLocationUpdates(
//                            LocationManager.NETWORK_PROVIDER,
//                            MIN_TIME_BW_UPDATES,
//                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//
//                    //Log.d("Network", "Network");
//
//                    if (locationManager != null)
//                    {
//                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        updateGPSCoordinates();
//                    }
//                }
//
//                //if GPS Enabled get lat/long using GPS Services
//                if (isGPSEnabled)
//                {
//                    if (location == null)
//                    {
//                        locationManager.requestLocationUpdates(
//                                LocationManager.GPS_PROVIDER,
//                                MIN_TIME_BW_UPDATES,
//                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//
//                        
//
//                        if (locationManager != null)
//                        {
//                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                            updateGPSCoordinates();
//                        }
//                    }
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            //e.printStackTrace();
//            //Log.e("Error : Location", "Impossible to connect to LocationManager", e);
//        }
//        //Toast.makeText(averseService.this, latitude.toString(), Toast.LENGTH_LONG).show();
//        return location;
//    }

	private void updateGPSCoordinates() {
		 if (location != null)
	        {
	            latitude = location.getLatitude();
	            longitude = location.getLongitude();
	        }
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
	
//	public void getLocation(){
//		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		   
//        // getting GPS status
//        Boolean isGPSEnabled = mLocationManager
//                .isProviderEnabled(LocationManager.GPS_PROVIDER);
//        //Toast.makeText(this, isGPSEnabled.toString(), Toast.LENGTH_LONG).show();
//
//        // getting network status
//        Boolean isNetworkEnabled = mLocationManager
//                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//        if (!isGPSEnabled && !isNetworkEnabled) {
//            // no network provider is enabled
//        	
//        } else  {
//            //this.canGetLocation = true;
//            if (isGPSEnabled) {
//            	
//                if (location == null) {
//                	
//                    mLocationManager.requestLocationUpdates(
//                            LocationManager.GPS_PROVIDER,
//                            1000,
//                            5, this);
//                    
//                    //Log.d("GPS", "GPS Enabled");
//                    if (mLocationManager != null) {
//                    	
//                        location = mLocationManager
//                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                        
//                        	
//                        if (location != null) {
//                        	
//                        	updateGPSCoordinates();
//                        	
//                        }
//                        else
//                        {
//                        	mLocationManager.requestLocationUpdates(
//        	                        LocationManager.NETWORK_PROVIDER,
//        	                        5*1000,
//        	                        10,  this);
//        	                //Log.d("Network", "Network Enabled");
//        	                if (mLocationManager != null) {
//        	                    location = mLocationManager
//        	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        	                    if (location != null) {
//        	                    	updateGPSCoordinates();
//        	                    	Toast.makeText(this, "Network Location", Toast.LENGTH_LONG).show();
//        	                    }
//        	                } 
//        	            
//                        }
//                    }
//                }
//                } else  {
//                	mLocationManager.requestLocationUpdates(
//	                        LocationManager.NETWORK_PROVIDER,
//	                        5*1000,
//	                        10,  this);
//	                //Log.d("Network", "Network Enabled");
//	                if (mLocationManager != null) {
//	                    location = mLocationManager
//	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//	                    if (location != null) {
//	                    		updateGPSCoordinates();	                    	
//	                    		Toast.makeText(this, "Network Location", Toast.LENGTH_LONG).show();
//	                    }
//	                } 
//                	
//                }
//            
//
//        }
//	}
	

	@Override
	public void onLocationChanged(Location location) {
		//Toast.makeText(averseService.this, "OnLocation Changed", Toast.LENGTH_LONG).show();
		//Location locationA=location;
		
		//updateGPSCoordinates();
		
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
        //mLocationManager.removeUpdates(this);
    	
        Toast.makeText(this, "Alarm destroyed ...", Toast.LENGTH_LONG).show();
          super.onDestroy();
          Toast.makeText(this, "Service destroyed ...", Toast.LENGTH_LONG).show();
    }

}
