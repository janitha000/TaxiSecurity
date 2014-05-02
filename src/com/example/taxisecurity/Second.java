package com.example.taxisecurity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Second extends Activity {
	TextView tv_name, tv_pass, tv_email;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
    tv_name = (TextView) findViewById(R.id.second_text_name);
    tv_email = (TextView) findViewById(R.id.second_text_email);
    tv_pass = (TextView) findViewById(R.id.second_text_pass);
    pref = getSharedPreferences("Registration", 0);
   // retrieving value from Registration
    String name = pref.getString("Name", null);
    String email = pref.getString("Email", null);
    String password = pref.getString("Password", null);
    
    // Now set these value into textview of second activity    
    tv_name.setText(name);
    tv_pass.setText(password);
    tv_email.setText(email);
     }
} 


