package com.example.policeDetails;

import android.app.ListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.map.mapActivity;
import com.example.taxisecurity.Database_Handler;
import com.example.taxisecurity.R;
import com.example.taxisecurity.R.id;
import com.example.taxisecurity.R.layout;

import android.app.AlertDialog;
//import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
//import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PoliceDisplayList extends ListActivity {
	Intent intent;
    TextView policeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);
        final Database_Handler db = new Database_Handler(this);
        

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        
        //db.open();
        ArrayList<HashMap<String, String>> policeList = db.getAllPolice_Details();


        if(policeList.size() != 0){
            final ListView listview = getListView();
            listview.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> arg0, View view,
                		final int position, final long id) {

                    policeName = (TextView) view.findViewById(R.id.policeName);
                    
                    final String policeNameValue = policeName.getText().toString();
                    System.out.println(policeNameValue);
                    //final int selectedPosition = position;

                    AlertDialog.Builder adb=new AlertDialog.Builder(PoliceDisplayList.this); 

                    
                    adb.setNeutralButton("Show Map", new DialogInterface.OnClickListener() {


         	
                    	
                    	

                  	public void onClick(DialogInterface dialog, int idNo) {
                  		
//                  		String selectedItem = (String) listview.getItemAtPosition(position);


//                  		String selectName=db.getNameFromId(id);
//                  		double lat = db.getLatitudeFromId(id);
//                  		double lon = db.getLongitudeFromId(id);

                  		
                  		double lat = db.getLatitudeFromId(policeNameValue);
                  		double lon = db.getLongitudeFromId(policeNameValue);


                  	    //Toast.makeText(getApplicationContext(), rowLat, Toast.LENGTH_SHORT).show();
                        //double lat = result.getDouble(result.getColumnIndex("KEY_LAN"));
                        //double lon = result.getDouble(result.getColumnIndex("KEY_LON"));


//***************edit  vindya****************************

//                    		String name="Maharagama Police Station";
//                    		Double Lat=6.845381;
//                    		Double Lon=79.928978;
                            Intent intent = new Intent(PoliceDisplayList.this, mapActivity.class);
                            intent.putExtra("Method", 1);
                            intent.putExtra("Latitiude", lat);
                            intent.putExtra("Longtitude",lon);

                            //intent.putExtra("Name", selectName);

                            intent.putExtra("Name", policeNameValue);


                            startActivity(intent);
                        
                        }
//*******************************************************************************************
                    });

                    adb.setNegativeButton("Cancel", null); 
                    adb.show();
                
                }
            });                
            ListAdapter adapter = new SimpleAdapter(
                    PoliceDisplayList.this, policeList, R.layout.item, 
                    new String[] {"policeID", "policeName", "policePhone","policeLan","policeLon"}, 
                    new int[] {R.id.policeID, R.id.policeName, R.id.policePhone, R.id.policeLan, R.id.policeLon});

            setListAdapter(adapter);
        }
        db.close();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.add_new, menu);
//        return true;
//    }

}
