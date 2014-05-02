package com.example.taxisecurity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class loginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	public void onClickOK(View v) {
		Intent averseIntent = new Intent(loginActivity.this, contactsActivity.class);
		startActivity(averseIntent);
		
		
	}

}
