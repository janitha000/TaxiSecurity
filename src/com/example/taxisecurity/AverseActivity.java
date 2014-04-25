package com.example.taxisecurity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class AverseActivity extends Activity {
	private AutoCompleteTextView autoComplete;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.averse);
		//Edit by Vindya
		String[] colors = getResources().getStringArray(R.array.Address);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,colors);
		autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
		// set adapter for the auto complete fields
		autoComplete.setAdapter(adapter);
		// specify the minimum type of characters before drop-down list is shown
		autoComplete.setThreshold(1);
		// comma to separate the different colors
		
		
	}

}
