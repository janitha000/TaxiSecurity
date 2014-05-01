package com.example.taxisecurity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class contactsActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addcontacts);
	} 
	public void onClickSave(View v){
		Intent intent=new Intent(contactsActivity.this,MainActivity.class);
		startActivity(intent);
		
	}
}
