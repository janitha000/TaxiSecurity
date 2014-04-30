package com.example.taxisecurity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SharedPreference extends Activity {

	  SharedPreferences pref;
	  Editor editor;
	  Button btn_register;
	  EditText et_name, et_pass, et_email;
	  
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginform);
		et_name = (EditText) findViewById(R.id.editText_name);
	    et_pass = (EditText) findViewById(R.id.editText_password);
	    et_email = (EditText) findViewById(R.id.editText_mail);
	    btn_register = (Button) findViewById(R.id.button_register);
	    
	    // creating an shared Preference file for the information to be stored
	    // first argument is the name of file and second is the mode, 0 is private mode
	    
	    pref = getSharedPreferences("Registration", 0);
	    // get editor to edit in file
	    editor = pref.edit();
	    
	    
	    // the tap of button we will fetch the information from three edittext    
	    btn_register.setOnClickListener(new View.OnClickListener() {
	        
	        @Override
	        public void onClick(View v) {
	        String name = et_name.getText().toString();
	        String email = et_email.getText().toString();
	        String pass = et_pass.getText().toString();
	        
	        if(et_name.getText().length()<=0){
	            Toast.makeText(SharedPreference.this, "Enter name", Toast.LENGTH_SHORT).show();
	        }
	        else if( et_email.getText().length()<=0){
	            Toast.makeText(SharedPreference.this, "Enter email", Toast.LENGTH_SHORT).show();
	        }
	        else if( et_pass.getText().length()<=0){
	            Toast.makeText(SharedPreference.this, "Enter password", Toast.LENGTH_SHORT).show();
	        }
	        else{
	        
	        // as now we have information in string. Lets stored them with the help of editor
	        editor.putString("Name", name);
	        editor.putString("Email",email);
	        editor.putString("Password",pass);
	        editor.commit();   // commit the values
	         
	        // after saving the value open next activity
	        Intent ob = new Intent(SharedPreference.this, Second.class);
	        startActivity(ob);
	        
	        }    
	        }
	    });
	   }
	} 
	  
	  
	    
	    
	    
	    

