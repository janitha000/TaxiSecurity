package com.example.taxisecurity;

import java.util.List;







import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ReceiveTransitionsIntentService extends IntentService {

	
	public static final String TRANSITION_INTENT_SERVICE = "ReceiveTransitionsIntentService";

    public ReceiveTransitionsIntentService() {
        super(TRANSITION_INTENT_SERVICE);
    }
	
    @Override
    protected void onHandleIntent(Intent intent) {
        if (LocationClient.hasError(intent)) {
            //todo error process
        } else {
            int transitionType = LocationClient.getGeofenceTransition(intent);
            if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER ||
                    transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
                List<Geofence> triggerList = LocationClient.getTriggeringGeofences(intent);
                for (Geofence geofence : triggerList) {
                    Log.i("test", "triggered Id " + geofence.getRequestId());
                }
            }
            generateNotification(transitionType);
        }
    }

        private void generateNotification(int type) {
        	
        	Intent notifyIntent = new Intent(this, MainActivity.class);
        	PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        	NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.common_signin_btn_text_normal_dark)
                            .setContentTitle("Geo Fence Notification")
                            .setContentText("HELLO")
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_SOUND);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify( 1, builder.build());
        }
	
}
