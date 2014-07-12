package com.example.settings;

import com.example.taxisecurity.R;
import com.example.taxisecurity.R.id;
import com.example.taxisecurity.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {
	
	private EditText contact1name; 
	private EditText contact1no; 
	private EditText contact2name; 
	private EditText contact2no; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		 contact1name = (EditText) findViewById(R.id.contact1name);
		 contact1no = (EditText) findViewById(R.id.contact1no);
	     contact2name = (EditText) findViewById(R.id.contact2name);
		 contact2no = (EditText) findViewById(R.id.contact2no);
		 
		SharedPreferences preferences = getSharedPreferences("ContactData", 0);
			contact1name.setText(preferences.getString("chosen1Name"," "));
			contact1no.setText(preferences.getString("chosen1No", " "));
		    contact2name.setText(preferences.getString("chosen2Name", " "));
			contact2no.setText(preferences.getString("chosen2No", " "));
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 contact1name = (EditText) findViewById(R.id.contact1name);
				 contact1no = (EditText) findViewById(R.id.contact1no);
			     contact2name = (EditText) findViewById(R.id.contact2name);
				 contact2no = (EditText) findViewById(R.id.contact2no);
				
				 
				 
				 AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
				 builder.setMessage("Save changes?");
				 builder.setCancelable(false);
				 builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					Settings.this.finish();
						
					}
				});
				 
				 builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				 
				 AlertDialog alert = builder.create();
				 alert.show();
				
				
				
				// SharedPreferences preferences = getSharedPreferences("ContactData", 0);
				//contact1name.setText(preferences.getString("chosen1Name"," "));
				//contact1no.setText(preferences.getString("chosen1No", " "));
				//contact2name.setText(preferences.getString("chosen2Name", " "));
				//contact2no.setText(preferences.getString("chosen2No", " "));
			}
			
			
		});
	}
	  
	
	@Override
	protected void onStop() {
		
		super.onStop();
		
		SharedPreferences preferences = getSharedPreferences("ContactData", 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("chosen1Name",contact1name.getText().toString());
		editor.putString("chosen1No",contact1no.getText().toString());
		editor.putString("chosen2Name",contact2name.getText().toString());
		editor.putString("chosen2No",contact2no.getText().toString());
		
		
		Toast.makeText(Settings.this, "Saved", Toast.LENGTH_LONG).show();
		editor.commit();
	}

}
