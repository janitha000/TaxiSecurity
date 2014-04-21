package com.example.taxisecurity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        //Buttons on Main Activity
        Button TimeButton = (Button) findViewById(R.id.Button01);
        Button AverseButton = (Button) findViewById(R.id.Button02);
        Button policeButton = (Button) findViewById(R.id.Button03);
        Button helpButton = (Button) findViewById(R.id.Button04);
        Button mapb = (Button) findViewById(R.id.button1);
        
        //map button, testing sadaha pamanayi!! meka passe delete karanawa
        //meka add kale Vindya
        //mekath 
        	mapb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent averseIntent = new Intent(MainActivity.this, mapActivity.class);
				startActivity(averseIntent);
				
				
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
        
        //Averse Button click Listener
        AverseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent averseIntent = new Intent(MainActivity.this, AverseActivity.class);
				startActivity(averseIntent);
				
				
			}
		});
        
      //Police Button click Listener
      policeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent averseIntent = new Intent(MainActivity.this, policeDetailActivity.class);
				startActivity(averseIntent);
				
				
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
            return true;
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
    
   

}
