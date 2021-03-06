package com.example.taxisecurity;

import java.util.Arrays;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import android.R.string;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class mapActivity extends FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener, com.google.android.gms.location.LocationListener{
	
	GoogleMap Mmap;
	boolean showMapActivated;
	Location location;
	
	
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
    
    // Define an object that holds accuracy and frequency parameters
    LocationRequest mLocationRequest;
    LocationClient mLocationClient;
	
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_map);
	    
	    
      MapFragment mapFragment = ((MapFragment) this
               .getFragmentManager().findFragmentById(R.id.map));
		Mmap= ((MapFragment) this.getFragmentManager().findFragmentById(R.id.map)).getMap();
		Mmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		Mmap.setMyLocationEnabled(true);
		
		Intent i = getIntent();
	    int method= i.getIntExtra("Method", 0);
	    switch(method){
	    case 1: //Show police Station
	    	Double Lati=i.getDoubleExtra("Latitiude",0);
	    	Double Long= i.getDoubleExtra("Longtitude",0);
	    	String name=i.getStringExtra("Name");
	    	showPoliceMap(Lati,Long, name);
	    	break;
	    case 2: //Show current Location in the map
	    	Double Lat = i.getDoubleExtra("DestinationLat",0);
			Double Lon = i.getDoubleExtra("DestinationLon",0);
	    	
	    	showMap(Lat, Lon);
	    	break;
	    case 0:
	    	break;
	    }
		
		//showPoliceMap();
}

	public void showMap(Double lat, Double lon){
		Mmap.addMarker(new MarkerOptions()
        .position(new LatLng(lat, lon))
        .title("Destination"));                 //************show the path using google API
		showMapActivated=true;
		// Create the LocationRequest object
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
        LatLng startPoint = new LatLng(lat, lon);  //need to show current location
		   CameraPosition cameraPosition = new CameraPosition.Builder().target(startPoint).tilt(15).zoom(14).bearing(0).
		        build();
		   Mmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        
	}
	
	public void showPoliceMap(Double lat, Double lon, String name){
//		List<LatLng> points = Arrays.asList(new LatLng(6.822334, 79.989991), new LatLng(6.8122, 79.97663), new LatLng(6.79433, 79.93887), new LatLng(6.822334, 79.989991));
//		PolygonOptions options = new PolygonOptions();
//	    options.addAll(points);
//	    options.strokeColor(Color.RED);
//	    options.fillColor(Color.BLUE);
//	    Mmap.addPolygon(options);
		Mmap.addMarker(new MarkerOptions()
        .position(new LatLng(lat, lon))
        .title(name));   
		 LatLng startPoint = new LatLng(lat, lon);
		   CameraPosition cameraPosition = new CameraPosition.Builder().target(startPoint).tilt(15).zoom(16).bearing(0).
		        build();
		   Mmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		   
		   //edit by vindya
//		   Intent intent = getIntent();
//		   if (intent != null) {
//		       if (Intent.ACTION_VIEW.equals(intent.getAction())) {
//		           if (intent.getData() != null) {
//		               int selectedPosition = Integer.parseInt(intent.getData().getHost());
//		               String[] policeLocations = getResources().getStringArray(R.id.Name);
//		               String location = policeLocations[selectedPosition];
//		               //...
//		           }
//		       }
//		   } 
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
	public void onLocationChanged(Location location) {
		String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        
        
		
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
	protected void onStop() {
		if(showMapActivated){
		if (mLocationClient.isConnected()) {
            /*
             * Remove location updates for a listener.
             * The current Activity is the listener, so
             * the argument is "this".
             */
	     //removeLocationUpdates(this);
			
           
        }
        /*
         * After disconnect() is called, the client is
         * considered "dead".
         */
        mLocationClient.disconnect();
		}
		super.onStop();
	}
	

}
