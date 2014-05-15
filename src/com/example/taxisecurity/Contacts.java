package com.example.taxisecurity;

import com.example.taxisecurity.MainActivity;
import com.example.taxisecurity.Contacts;
import com.example.taxisecurity.Appconfig;
import com.example.taxisecurity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Contacts extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(Contacts.this,MainActivity.class ));
				
				AlertDialog.Builder builder = new AlertDialog.Builder(Contacts.this);
				builder.setMessage("Are you sure?");
				builder.setCancelable(false);
				builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Contacts.this,MainActivity.class);
					startActivity(intent);
						
					}
				});
				
				builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						
					}
				});
				
				AlertDialog alert = builder.create(); 
				alert.show();
				
					
				
			}
		});
		
		Appconfig.activity=true;
		
		
		
	}

}
