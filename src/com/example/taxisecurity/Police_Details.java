package com.example.taxisecurity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Police_Details extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.policedetail);
         
        DatabaseHandler db = new DatabaseHandler(this);
         
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting .."); 
        db.addPolice(new Police("Kuruduwaththa", "0112234778"));        
        db.addPolice(new Police("Kohuwala", "9199999999"));
        db.addPolice(new Police("Kirulapana", "9522222222"));
        db.addPolice(new Police("Wellawaththa", "9533333333"));
         
        // Reading all contacts
        Log.d("Reading: ", "Reading all police_details.."); 
        List<Police> police_station = db.getAllPolice();       
         
        for (Police cn : police_station) {
            String log = "Id: "+cn.getID()+" ,Police Station Name: " + cn.getName() + " ,Phone NUmber: " + cn.getPhoneNumber();
                // Writing Contacts to log
        Log.d("Details: ", log);
    }
	}
	
}
