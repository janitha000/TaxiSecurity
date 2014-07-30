package com.example.taxisecurity;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class MyLocation extends Service implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener, com.google.android.gms.location.LocationListener{

	public static double latitude;
	public static double longitude;
	Location location;
	LocationRequest mLocationRequest;
    LocationClient mLocationClient;
	// Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL =
            MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    
    @Override
    public void onStart(Intent intent, int startId) {
    	//Toast.makeText(this, "Started MyLocation", Toast.LENGTH_SHORT).show();
    	connect();
    	super.onStart(intent, startId);
    }
    public void connect(){
    	
    mLocationRequest = LocationRequest.create();
    // Use high accuracy
    mLocationRequest.setPriority(
            LocationRequest.PRIORITY_HIGH_ACCURACY);
    // Set the update interval to 5 seconds
    mLocationRequest.setInterval(UPDATE_INTERVAL);
    // Set the fastest update interval to 1 second
    mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
	
    mLocationClient = new LocationClient(MyLocation.this, this, this);
   	mLocationClient.connect();
    
    }
    
	@Override
	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		//Toast.makeText(this, "MyLOCATION "+latitude + " " + longitude, Toast.LENGTH_SHORT).show();
		
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

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
		
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		 mLocationClient.disconnect();
	Toast.makeText(this, "MyLocation Destroyed", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

	
}
