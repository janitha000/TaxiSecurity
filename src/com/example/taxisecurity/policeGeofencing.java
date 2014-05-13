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
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class policeGeofencing extends Service implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener,
LocationClient.OnAddGeofencesResultListener {
	
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

	    mLocationClient = new LocationClient(this, this, this);
	    mLocationClient.connect();
	    Toast.makeText(this, "GeoFences Added", Toast.LENGTH_LONG).show();
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
	}

	@Override
	public void onDisconnected() {
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
	
	
	
	
}
