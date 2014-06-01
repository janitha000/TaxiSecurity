package com.example.taxisecurity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	

	 // User Session Manager Class
   // UserSessionManager session;
     
    // Button Logout
    //Button btnLogout;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	 
    	 super.onCreate(savedInstanceState);
        //setContentView(R.layout.loginform);
       setContentView(R.layout.activity_main);
        
      if(first_time_check()){
    	  Intent contactIntent = new Intent(MainActivity.this, contactDetails.class);
    	  startActivity(contactIntent);
      }
     

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
      //**********New Installation login form*****************
        
        
           
    //*************End of editing**********************

       // Start LOCATION Service
        Intent in = new Intent(MainActivity.this, MyLocation.class);
        startService(in);
       
        //Buttons on Main Activity
        Button TimeButton = (Button) findViewById(R.id.Button01);
        Button AverseButton = (Button) findViewById(R.id.Button02);
        Button policeButton = (Button) findViewById(R.id.Button04);
        Button helpButton = (Button) findViewById(R.id.Button03);
        Button mapb = (Button) findViewById(R.id.button1);
        Button serviceb = (Button) findViewById(R.id.button2);
        /*me button 1th only testing. installation 1di weda karanna nemi hadala thiyenne,thawa modify karanna one,second activity 1 demme output 1k balanna*/
        Button register = (Button) findViewById(R.id.regButton);
        //map button, testing sadaha pamanayi!! meka passe delete karanawa
        Button averse = (Button) findViewById(R.id.averseButton); // Only for testing
        Button geo = (Button) findViewById(R.id.button4);
        
        geo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent averseIntent = new Intent(MainActivity.this, policeGeofencing.class);
				
				startService(averseIntent);
				
				
			}
		});


        averse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent averseIntent = new Intent(MainActivity.this, averseService.class);
				//averseIntent.putExtra("Method", 2);
				startService(averseIntent);
				
				
			}
		});



        
        	mapb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent averseIntent = new Intent(MainActivity.this, mapActivity.class);
				startActivity(averseIntent);
				
				
			}
		});
        	
        	//Service Button click Listener , passe delete karanawa, test karanna witharai
            serviceb.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				startService(new Intent(MainActivity.this,smsService.class));
    				
    				
    			}
    		});
        
      //Time Button click Listener
        TimeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent averseIntent = new Intent(MainActivity.this, timeAllocatorActivity.class);
				startActivity(averseIntent);
				
				
			}
		});
        //edited by vindya
        //Averse Button click Listener
        AverseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent averseIntent = new Intent(MainActivity.this, PlacesAutocompleteActivity.class);
				startActivity(averseIntent);
				
				
			}
		});
        Database_Handler db= new Database_Handler(this);
     // Inserting Contacts
        //Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Fort", "0112433744" , 6.933825 , 79.846080));
        db.addContact(new Contact("Pettah", "0112320389",6.936655 ,79.850126));
        db.addContact(new Contact("Keselwatta", "0112421944",6.935062,79.858924));
        db.addContact(new Contact("Slave Island", "0112433820",6.927494,79.849151));
        db.addContact(new Contact("Maradana", "0112692748",6.927976 ,79.865450));
        db.addContact(new Contact("Maligawatta", "0112434135",6.934399 ,79.869878));
        db.addContact(new Contact("Harbour", "0112320980",6.941919 ,79.842861));
        db.addContact(new Contact("Foreshore", "0112434595",6.949690 ,79.857366));
        db.addContact(new Contact("Kotahena", "0112431861",6.951129 ,79.862432));
        db.addContact(new Contact("Modara", "0112524411",6.964794 ,79.868256));
        db.addContact(new Contact("Mattakkuliya", "0112521761",6.976244 ,79.879897));
        db.addContact(new Contact("Grandpass", "0112412414",6.949085 ,79.873028));
        db.addContact(new Contact("Dematagoda", "0112693838",6.930688 ,79.877067));
        db.addContact(new Contact("Kirulapone", "0112323689",6.877824 ,79.873905));
        db.addContact(new Contact("Narahenpita", "0112388242",6.891926 ,79.876672));
        db.addContact(new Contact("Wellawatta", "0112588212",6.873218 ,79.860997));
        db.addContact(new Contact("Bambalapitiya", "0112588680",6.892082 ,79.855808));
        db.addContact(new Contact("Kollupitiya", "0112323689",6.914184 ,79.849107));
        db.addContact(new Contact("Borella", "0112693938",6.915350 ,79.878191));
        db.addContact(new Contact("Cinamon Gardens", "0112695411",6.907193 ,79.863431));
        db.addContact(new Contact("Kelaniya", "0112911922",6.950398 ,79.917544));
        db.addContact(new Contact("Peliyagoda", "0112911222",6.955228 ,79.886102));
        db.addContact(new Contact("Kohuwala", "0112852621",6.867370 ,79.883629));
        db.addContact(new Contact("Mirihana", "0112852566",6.873928 ,79.901043));
        db.addContact(new Contact("Maharagama", "0112850222",6.845210 ,79.928852));
        db.addContact(new Contact("Dehiwala", "0112283722",6.849982 ,79.871518));
        db.addContact(new Contact("Boralesgamuwa", "0112509461",6.841546 ,79.900786));
        
        
        
      //Police Button click Listener
      policeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent averseIntent = new Intent(MainActivity.this, DisplayList.class);
				startActivity(averseIntent);
				
				
			}
		});
        
      register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent regIntent = new Intent(MainActivity.this, contactDetails.class);
				startActivity(regIntent);
				
				
			}
		});
      
      //Help Button click Listener
        helpButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent averseIntent = new Intent(MainActivity.this, helpActivity.class);
				startActivity(averseIntent);
				
				
			}
		});
        

 	if (getIntent().getBooleanExtra("EXIT", false)) {
 		finish();
	}
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
           Intent settingsIntent =  new Intent(MainActivity.this,Settings.class);
           startActivity(settingsIntent);
        }
        else if (id == R.id.action_about) {
			Intent aboutIntent = new Intent(MainActivity.this, aboutActivity.class);
			startActivity(aboutIntent);
		}
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    @Override
    protected void onResume() {
    	Toast.makeText(this, MyLocation.latitude + " " + MyLocation.longitude, Toast.LENGTH_SHORT).show();
    	super.onResume();
    	
    }
    
    private boolean first_time_check() {
        SharedPreferences uPreferences=getSharedPreferences("ContactData",Context.MODE_PRIVATE);
        String first = uPreferences.getString("first", null);
        if((first == null)){
            return true;
        }
        else 
            return false;
    }
    
    @Override
    protected void onDestroy() {
    	
    	super.onDestroy();
    }
   

}
