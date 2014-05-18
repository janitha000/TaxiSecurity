package com.example.taxisecurity;

import java.util.ArrayList;
import java.util.List;

import com.example.taxisecurity.geofenceObject.SimpleGeofence;
import com.example.taxisecurity.geofenceObject.SimpleGeofenceStore;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class policeGeofencing extends Service implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener,
LocationClient.OnAddGeofencesResultListener, LocationListener {
	long NEVER_EXPIRE = -1;
	int GEOFENCE_TRANSITION_ENTER = 1;
	int GEOFENCE_TRANSITION_EXIT = 2;
	
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
	
    LocationRequest mLocationRequest;
	private LocationClient mLocationClient;
	private List<Geofence> mGeofenceLists = new ArrayList<Geofence>();
	
	@Override
	public void onCreate() {
	    super.onCreate();
	    
	    
	    
	    

	    Geofence geofence1 = new Geofence.Builder()
	            .setRequestId("your target place")
	            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
	            .setCircularRegion(7.352802, 80.616922,50.0f)
	            .setExpirationDuration(Geofence.NEVER_EXPIRE)
	            .build();

	    mGeofenceLists.add(geofence1);
	    Toast.makeText(this, "GeoFence1 Added", Toast.LENGTH_LONG).show();
	    
	    Geofence geofence2 = new Geofence.Builder()
        .setRequestId("your target place")
        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
        .setCircularRegion(7.289406, 80.607608,10)
        .setExpirationDuration(Geofence.NEVER_EXPIRE)
        .build();
	    
	    mGeofenceLists.add(geofence2);
	    Toast.makeText(this, "GeoFence2 Added", Toast.LENGTH_LONG).show();
	    mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
	    mLocationClient = new LocationClient(this, this, this);
	    mLocationClient.connect();
	    
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
	    return null;
	}

	private PendingIntent getPendingIntent() {
	    Intent intent = new Intent(this, ReceiveTransitionsIntentService.class);
	    return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	public void onConnected(Bundle bundle) {
	    mLocationClient.addGeofences(mGeofenceLists, getPendingIntent(), this);
	    mLocationClient.requestLocationUpdates(mLocationRequest, this);
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	}

	@Override
	public void onDestroy() {
	    mLocationClient.disconnect();
	    super.onDestroy();
	}

	@Override
	public void onAddGeofencesResult(int i, String[] strings) {
	    if (LocationStatusCodes.SUCCESS == i) {
	        //todo check geofence status
	    } else {
	    }
	}


	@Override
	public void onLocationChanged(Location location) {
		String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        
		
	}
	
	
	
	
}
