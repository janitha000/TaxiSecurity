package com.example.taxisecurity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
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

public class DisplayList extends ListActivity {
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
        ArrayList<HashMap<String, String>> policeList = db.getAllContact();


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
                    AlertDialog.Builder adb=new AlertDialog.Builder(DisplayList.this); 
<<<<<<< HEAD
                   
                   adb.setNeutralButton("Show Map", new DialogInterface.OnClickListener() {
//
//
//         	
//                    	
//                    	
//
                  	public void onClick(DialogInterface dialog, int id) {
//                  		
// //                 		String selectedItem = (String) listview.getItemAtPosition(position);
=======
                    
                    adb.setNeutralButton("Show Map", new DialogInterface.OnClickListener() {


         	
                    	
                    	

                  	public void onClick(DialogInterface dialog, int idNo) {
                  		
//                  		String selectedItem = (String) listview.getItemAtPosition(position);
>>>>>>> 3ca9000e6dea0a5ddb684a937f89160341f04a72
//                  		String query = "SELECT KEY_LAN,KEY_LON FROM TABLE_POLICE WHERE KEY_NAME =  '" +selectedItem  + "'";
//                        SQLiteDatabase dbs = db.getReadableDatabase();
//                        Cursor result = dbs.rawQuery(query, null);
//                        result.moveToFirst();
<<<<<<< HEAD
                  		String selectName=db.getNameFromId(id);
                  		double lat = db.getLatitudeFromId(id);
                  		double lon = db.getLongitudeFromId(id);
=======
                  		
                  		double lat = db.getLatitudeFromId(policeNameValue);
                  		double lon = db.getLongitudeFromId(policeNameValue);
>>>>>>> 3ca9000e6dea0a5ddb684a937f89160341f04a72
                  	    //Toast.makeText(getApplicationContext(), rowLat, Toast.LENGTH_SHORT).show();
                        //double lat = result.getDouble(result.getColumnIndex("KEY_LAN"));
                        //double lon = result.getDouble(result.getColumnIndex("KEY_LON"));


//***************edit  vindya****************************

//                    		String name="Maharagama Police Station";
//                    		Double Lat=6.845381;
//                    		Double Lon=79.928978;
                            Intent intent = new Intent(DisplayList.this, mapActivity.class);
                            intent.putExtra("Method", 1);
                            intent.putExtra("Latitiude", lat);
                            intent.putExtra("Longtitude",lon);
<<<<<<< HEAD
                            intent.putExtra("Name", selectName);
=======
                            intent.putExtra("Name", policeNameValue);
>>>>>>> 3ca9000e6dea0a5ddb684a937f89160341f04a72
                            startActivity(intent);
                        
                        }
//*******************************************************************************************
                    });

                    adb.setNegativeButton("Cancel", null); 
                    adb.show();
                
                }
            });                
            ListAdapter adapter = new SimpleAdapter(
                    DisplayList.this, policeList, R.layout.item, 
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

