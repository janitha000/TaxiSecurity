package com.example.taxisecurity;

import java.util.List;

import android.R.color;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Police_Details extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//super.onCreate(savedInstanceState);
		
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
        StringBuilder builder = new StringBuilder();
        for (Police c: police_station)
        {               
            builder.append(c.getID()).append(";")
                .append(c.getName()).append(";")
                .append(c.getPhoneNumber()).append(";");
                
        }
        //tv.setText(builder.toString());

        builder.toString();

        String st = new String(builder);
        Log.d("Main",st);
        String[] rows  = st.split("_");
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tab);
        tableLayout.removeAllViews();

        for(int i=0;i<rows.length;i++){
            Log.d("Rows",rows[i]);
            String row  = rows[i];
            TableRow tableRow = new TableRow(getApplicationContext());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            final String[] cols = row.split(";");

            Handler handler = null;

            for (int j = 0; j < cols.length; j++) {             
                final String col = cols[j];                                 
                TextView columsView = new TextView(getApplicationContext());
                columsView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                columsView.setTextColor(color.black);
                columsView.setText(String.format("%7s", col));                                
                Log.d("Cols", String.format("%7s", col));
                tableRow.addView(columsView);

            }
        }
	}
	
}
