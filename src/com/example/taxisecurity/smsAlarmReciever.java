package com.example.taxisecurity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class smsAlarmReciever extends BroadcastReceiver {

	SmsManager smsManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
		String name=intent.getStringExtra("class");
	    Toast.makeText(context, name, Toast.LENGTH_LONG).show();
		
	}
	
	
	
	
//	public void onReceive(Context context, Intent intent) {
//
//	    // TODO Auto-generated method stub
//		Toast.makeText(context, "Recieving", Toast.LENGTH_LONG).show();
//	     System.out.println("BroadCast\n");
//	     //String name=intent.getStringExtra("class");
//	     //Toast.makeText(context, name, Toast.LENGTH_LONG).show();
//
//	}
//	//@Override
////	public void onReceive(Context context, Intent intent) {
////		//String sms = intent.getStringExtra("sms");
////		String sms = "Multiple msg";
////		String phoneNo = "0716544588";
////	try {
////			
////			smsManager.sendTextMessage(phoneNo, null, sms, null, null);
//////			Toast.makeText(getApplicationContext(), "SMS Sent!",
//////					Toast.LENGTH_LONG).show();
////		} catch (Exception e) {
//////			Toast.makeText(getApplicationContext(),
//////					"SMS faild, please try again later!",
//////					Toast.LENGTH_LONG).show();
////			e.printStackTrace();
////		}
////
////	}

}
