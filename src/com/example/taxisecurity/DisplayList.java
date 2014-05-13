package com.example.taxisecurity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
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

public class DisplayList extends ListActivity {
	Intent intent;
    TextView policeID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);
        Database_Handler db = new Database_Handler(this);
        

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        
        //db.open();
        ArrayList<HashMap<String, String>> policeList = db.getAllContact();


        if(policeList.size() != 0){
            ListView listview = getListView();
            listview.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> arg0, View view,
                		final int position, long id) {

                    policeID = (TextView) view.findViewById(R.id.policeID);

                    String policeIDValue = policeID.getText().toString();
                    final int selectedPosition = position;
                    AlertDialog.Builder adb=new AlertDialog.Builder(DisplayList.this); 
                    
                    adb.setNeutralButton("Show Map", new DialogInterface.OnClickListener() {
                    	public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("police:" + selectedPosition), DisplayList.this, mapActivity.class);
                            startActivity(intent);
                        }
                    });

                    adb.setNegativeButton("Cancel", null); 
                    adb.show();
                
                }
            });                
            ListAdapter adapter = new SimpleAdapter(
                    DisplayList.this, policeList, R.layout.item, 
                    new String[] {"policeID", "policeName", "policePhone"}, 
                    new int[] {R.id.policeID, R.id.policeName, R.id.policePhone});

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

