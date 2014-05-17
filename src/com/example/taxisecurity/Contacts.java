package com.example.taxisecurity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Contacts extends Activity {
	EditText Uname= (EditText) findViewById(R.id.UserName);
	EditText Upwd= (EditText) findViewById(R.id.Pwd);
	EditText c1name= (EditText) findViewById(R.id.contact1Name);
	EditText c1no= (EditText) findViewById(R.id.contact1No);
	EditText c2name= (EditText) findViewById(R.id.contact2Name);
	EditText c2no= (EditText) findViewById(R.id.contact2No);
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
		
		 
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(Contacts.this,MainActivity.class ));
				
				//*************8Edited by Janitha*******************
				SharedPreferences prefernces = getSharedPreferences("ContactData",Context.MODE_PRIVATE );
				SharedPreferences.Editor editor = prefernces.edit();
				
				editor.putString("username", Uname.getText().toString() );
				editor.putString("password", Upwd.getText().toString() );
				editor.putString("chosen1Name", c1name.getText().toString() );
				editor.putString("chosen1No", c1no.getText().toString() );
				editor.putString("chosen2Name", c2name.getText().toString() );
				editor.putString("chosen2No", c2no.getText().toString() );
				
				editor.commit();
				
				
//				AlertDialog.Builder builder = new AlertDialog.Builder(Contacts.this);
//				builder.setMessage("Are you sure?");
//				builder.setCancelable(false);
//				builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//					Intent intent = new Intent(Contacts.this,MainActivity.class);
//					startActivity(intent);
//						
//					}
//				});
//				
//				builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.cancel();
//						
//					}
//				});
//				
//				AlertDialog alert = builder.create(); 
//				alert.show();
				
					
				
			}
		});
		
		Appconfig.activity=true;
		
		
		
	}

}
