package com.example.taxisecurity;

import android.app.Activity;
import android.R.layout;
import android.app.Activity;
import android.app.PendingIntent.CanceledException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class timeAllocatorActivity extends Activity implements View.OnClickListener {

	private Button buttonStartTime, buttonStopTime, buttonExtend;
	private EditText edtHours, edtMinutes;
	private LinearLayout layoutRemaining;   	 
	private CountDownTimer countDownTimer; 
	private TextView textViewRemaining;	
	private TextView textViewShowTime;
	
	private long totalTimeCountInMilliseconds; 
	private long timeRemaining;										
	private long timeBlinkInMilliseconds;
	private boolean blink; 
	private boolean error=false;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);

		buttonStartTime = (Button) findViewById(R.id.btnStartTime);
		buttonStopTime = (Button) findViewById(R.id.btnStopTime);
		buttonExtend = (Button) findViewById(R.id.btnExtendTime);
		textViewShowTime = (TextView) findViewById(R.id.tvTimeCount);
		edtMinutes = (EditText) findViewById(R.id.edtMinutes);
		edtHours = (EditText) findViewById(R.id.edtHours);
		layoutRemaining = (LinearLayout) findViewById(R.id.layoutRemaining);

		buttonStartTime.setOnClickListener(this);
		buttonStopTime.setOnClickListener(this);
		buttonExtend.setOnClickListener(this);


	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnStartTime) {
			textViewShowTime.setTextAppearance(getApplicationContext(),
					R.style.normalText);
			
			setTimer();
			
			buttonStopTime.setVisibility(View.VISIBLE);
			layoutRemaining.setVisibility(View.VISIBLE);
			buttonStartTime.setVisibility(View.GONE);
			edtMinutes.setVisibility(View.GONE);
			edtHours.setVisibility(View.GONE);
			edtMinutes.setText("");
			edtHours.setText("");
			
			startTimer();

		} else if (v.getId() == R.id.btnStopTime) {
			countDownTimer.cancel();
			
			buttonStartTime.setVisibility(View.VISIBLE);
			buttonStopTime.setVisibility(View.GONE);
			layoutRemaining.setVisibility(View.GONE);
			edtMinutes.setVisibility(View.VISIBLE);
			edtHours.setVisibility(View.VISIBLE);

		}  else if (v.getId() == R.id.btnExtendTime) {
			countDownTimer.cancel();
			buttonExtend.setVisibility(View.GONE);	
			update();
			buttonStopTime.setVisibility(View.VISIBLE);	
			Toast.makeText(timeAllocatorActivity.this, "Allocated time is extended by 10 mins....",
			Toast.LENGTH_LONG).show();

		} 
	}
	private void update(){
			countDownTimer = new CountDownTimer(timeRemaining+(1*60*1000), 500){
			
			@Override
			public void onTick(long leftTimeInMilliseconds) {
				textViewShowTime.setTextAppearance(getApplicationContext(),
						R.style.normalText);
				textViewShowTime.setVisibility(View.VISIBLE);
				long seconds = leftTimeInMilliseconds / 1000;
				if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {	
					
					textViewShowTime.setTextAppearance(getApplicationContext(),
							R.style.blinkText);
					buttonExtend.setVisibility(View.VISIBLE);
					if (blink) {
						textViewShowTime.setVisibility(View.VISIBLE);
					} else {
						textViewShowTime.setVisibility(View.INVISIBLE);
					}
					blink = !blink; // toggle the value of blink
				}
				
				textViewShowTime.setText(String.format("%02d", seconds / (60*60))
						+ ":" + String.format("%02d", (seconds % (60*60))/60)	
						+ ":" + String.format("%02d", seconds % 60));				
			}

				@Override
				public void onFinish() {
				
				}		
		}.start();
	}
	private void setTimer() {
		int hours = 0;
		int minutes= 0;
		int time= 0;
		
		if (!edtHours.getText().toString().equals("") && !edtMinutes.getText().toString().equals("") ) {
			minutes = Integer.parseInt(edtMinutes.getText().toString());
			hours = Integer.parseInt(edtHours.getText().toString());
			time =(60*hours) + minutes;
		}else if(edtHours.getText().toString().equals("") && !edtMinutes.getText().toString().equals("")){
			minutes = Integer.parseInt(edtMinutes.getText().toString());
			time=minutes;
		}else if(!edtHours.getText().toString().equals("") && edtMinutes.getText().toString().equals("")){
			hours = Integer.parseInt(edtHours.getText().toString());
			time=60*hours;
		} 
		else{
				error=true;
				Toast.makeText(timeAllocatorActivity.this, "Please Enter Travel Time...",
				Toast.LENGTH_LONG).show();						
		}
		
		totalTimeCountInMilliseconds = 60 * time * 1000;
		timeBlinkInMilliseconds = 50 * 1000;
	}

	private void startTimer() {
		countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
			@Override
			public void onTick(long leftTimeInMilliseconds) {
				timeRemaining =leftTimeInMilliseconds;
				long seconds = leftTimeInMilliseconds / 1000;
				if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
					textViewShowTime.setTextAppearance(getApplicationContext(),
							R.style.blinkText);
					buttonExtend.setVisibility(View.VISIBLE);
					if (blink) {
						textViewShowTime.setVisibility(View.VISIBLE);
					} else {
						textViewShowTime.setVisibility(View.INVISIBLE);
					}
					blink = !blink; 
				}
				textViewShowTime.setText(String.format("%02d", seconds / (60*60))
						+ ":" + String.format("%02d", (seconds % (60*60))/60)	
						+ ":" + String.format("%02d", seconds % 60));            
			}
			@Override
			public void onFinish() {
				if(totalTimeCountInMilliseconds!=0)
					textViewShowTime.setText("Time up!");
				else 
					textViewShowTime.setText("");
					textViewRemaining.setVisibility(View.VISIBLE);
					textViewShowTime.setVisibility(View.VISIBLE);
					buttonStartTime.setVisibility(View.VISIBLE);
					buttonStopTime.setVisibility(View.GONE);
					edtMinutes.setVisibility(View.VISIBLE);
					edtHours.setVisibility(View.VISIBLE);
				}
		}.start();

	}
}