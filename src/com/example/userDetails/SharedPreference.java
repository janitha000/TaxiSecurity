package com.example.userDetails;

import com.example.taxisecurity.R;
import com.example.taxisecurity.contactsActivity;
import com.example.taxisecurity.loginActivity;
import com.example.taxisecurity.R.id;
import com.example.taxisecurity.R.layout;

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
	  Button btn_ok;
	  EditText et_name, et_pass, et_email;
	  public static final String PREFS_NAME = "MyPrefsFile";
	private static final int INSTRUCTIONS_CODE = 0;
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		  
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		/******* Test edit here*/
		pref = getSharedPreferences("prefs", 0);
	    boolean firstRun = pref.getBoolean("firstRun", true);
	    if ( firstRun )
	    {
	        // here run your first-time instructions, for example :
	        startActivityForResult(
	             new Intent(getBaseContext(), loginActivity.class),
	             INSTRUCTIONS_CODE);

	    }
	 
	    /**************end of test edit*/
		et_name = (EditText) findViewById(R.id.edtname);
	    et_pass = (EditText) findViewById(R.id.edtpwd);
	   // et_email = (EditText) findViewById(R.id.editText_mail);
	    btn_ok = (Button) findViewById(R.id.btn_ok);
	    
	    // creating an shared Preference file for the information to be stored
	    // first argument is the name of file and second is the mode, 0 is private mode
	    
	    pref = getSharedPreferences("Registration", 0);
	    // get editor to edit in file
	    editor = pref.edit();
	    
	    
	    // the tap of button we will fetch the information from three edittext    
	    btn_ok.setOnClickListener(new View.OnClickListener() {
	        
	        @Override
	        public void onClick(View v) {
	        String name = et_name.getText().toString();
	      //  String email = et_email.getText().toString();
	        String pass = et_pass.getText().toString();
	        
	        if(et_name.getText().length()<=0){
	            Toast.makeText(SharedPreference.this, "Enter name", Toast.LENGTH_SHORT).show();
	        }
	       // else if( et_email.getText().length()<=0){
	         //   Toast.makeText(SharedPreference.this, "Enter email", Toast.LENGTH_SHORT).show();
	        //}
	        else if( et_pass.getText().length()<=0){
	            Toast.makeText(SharedPreference.this, "Enter password", Toast.LENGTH_SHORT).show();
	        }
	        else{
	        
	        // as now we have information in string. Lets stored them with the help of editor
	        editor.putString("Name", name);
	       // editor.putString("Email",email);
	        editor.putString("Password",pass);
	        editor.commit();   // commit the values
	         
	        // after saving the value open next activity
	        Intent ob = new Intent(SharedPreference.this, contactsActivity.class);
	        startActivity(ob);
	        
	        }    
	        }
	    });
	   }
	// when your InstructionsActivity ends, do not forget to set the firstRun boolean
	    protected void onActivityResult(int requestCode, int resultCode,
	            Intent data) {
	        if (requestCode == INSTRUCTIONS_CODE) {
	            SharedPreferences settings = getSharedPreferences("prefs", 0);
	            SharedPreferences.Editor editor = settings.edit();
	            editor.putBoolean("firstRun", true);
	            editor.commit();
	        }
	    }
	} 
	  
	  
	    
	    
	    
	    

