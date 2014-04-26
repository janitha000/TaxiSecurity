package com.example.taxisecurity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class testServiceActivity extends Activity {

	
//	private smsService mBoundService;
//	boolean mIsBound;
//
//	private ServiceConnection mConnection = new ServiceConnection() {
//	    public void onServiceConnected(ComponentName className, IBinder service) {
//	        // This is called when the connection with the service has been
//	        // established, giving us the service object we can use to
//	        // interact with the service.  Because we have bound to a explicit
//	        // service that we know is running in our own process, we can
//	        // cast its IBinder to a concrete class and directly access it.
//	        mBoundService = ((smsService.LocalBinder)service).getService();
//
//	        // Tell the user about this for our demo.
//	        //Toast.makeText(Binding.this, "Service connected",
//	        //        Toast.LENGTH_SHORT).show();
//	    }
//
//	    public void onServiceDisconnected(ComponentName className) {
//	        // This is called when the connection with the service has been
//	        // unexpectedly disconnected -- that is, its process crashed.
//	        // Because it is running in our same process, we should never
//	        // see this happen.
//	        mBoundService = null;
//	        //Toast.makeText(Binding.this, "Service Disconnected",
//	          //      Toast.LENGTH_SHORT).show();
//	    }
//	};
//	
//	void doBindService() {
//	    // Establish a connection with the service.  We use an explicit
//	    // class name because we want a specific service implementation that
//	    // we know will be running in our own process (and thus won't be
//	    // supporting component replacement by other applications).
//	    bindService(new Intent(Binding.this, 
//	            smsService.class), mConnection, Context.BIND_AUTO_CREATE);
//	     mIsBound = true;
//	}
//
//	void doUnbindService() {
//	    if (mIsBound) {
//	        // Detach our existing connection.
//	        unbindService(mConnection);
//	        mIsBound = false;
//	    }
//	}
//
//	@Override
//	protected void onDestroy() {
//	    super.onDestroy();
//	    doUnbindService();
//	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startService(new Intent(testServiceActivity.this, smsService.class));
	}
}

