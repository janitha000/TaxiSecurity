package com.example.shakeDetector;

import com.example.taxisecurity.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

public class startDetector extends Activity {
	boolean on=false;
	public int selected=-1;
	private int  restoredval=1;
int a;
SharedPreferences prefs;
SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences("My", this.MODE_PRIVATE);
		restoredval = prefs.getInt("chosen",1);
		Toast.makeText(startDetector.this, "start Detector",
					Toast.LENGTH_LONG).show();
		final CharSequence[] choice = {"On","Off"};	 
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Shake Detector status");
		alert.setSingleChoiceItems(choice,restoredval, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        if (choice[which] == "On") {
		        	restoredval=0;
		            on = true;
		        } else if (choice[which] == "Off") {
		        	restoredval=1;
		            on = false;
		        }
		        else {
		        	System.out.println("Didnt pick any value");
		        	
		        	if(restoredval==0){
		        		on=true;
		        	}else
		        		on=false;
		        }
		    }
		});
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	if (restoredval==0) {
		        	selected=0;
		            editor = prefs.edit();
				    editor.putInt("chosen", 0);
				    editor.commit();
		        	dialog.dismiss();
		        	dialog.cancel();
		        	Intent sensor=new Intent(startDetector.this, Detector.class);
		        	startService(sensor);
		        	Intent intent=new Intent(startDetector.this, MainActivity.class);
		            startActivity(intent);
		        } else if (restoredval==1) {
		        	selected=1;
		        	editor = prefs.edit();
				    editor.putInt("chosen", 1);
				    editor.commit();
		        	dialog.dismiss();
		        	dialog.cancel();
		        	Intent sensor=new Intent(startDetector.this, Detector.class);
		        	stopService(sensor);
		        	Intent intent=new Intent(startDetector.this, MainActivity.class);
		        	startActivity(intent);
		        }
		    }
		});
		alert.show();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		  SharedPreferences p=getSharedPreferences("my",MODE_PRIVATE );
		    p.edit().remove("editor");
		    p.edit().clear(); 
		    p.edit().commit();    
	}
}
