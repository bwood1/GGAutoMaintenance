package com.example.ggcautomaintenance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {

	String helpMain = 	"* Enter the Current Date in the Odometer. \n" +
			"* Select the Fill-Up check box if this mileage is being entered when filling-up the tank. \n" +
			"* Select Enter to save the current mileage. \n" +
			"* Select Calculate MPG to go to the Calculator Screen. \n" +
			"* Select Show Maintenance to go to the Maintenance Items List screen.";

	private static final String KEY_VALUE = "OdometerValue";
	private static Odometer mOdometer;
	private int mOdometerValue;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mOdometer = (Odometer) findViewById(R.id.odometer);
		
	    if(savedInstanceState != null)
	    {
	        mOdometerValue = savedInstanceState.getInt(KEY_VALUE);
	        mOdometer.setValue(mOdometerValue);
	    }
	}	
	
	public void enterButton(View view)
	{
		
		Button button = (Button) findViewById(R.id.enterButton);
		button.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				
				setCurrentMileage();
				
			}
		});				
	}

	public void mpgButton(View view)		
	{
		Button button = (Button) findViewById(R.id.buttonCalcMPG);
		button.setOnClickListener(new View.OnClickListener() {    
			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MilesPerGallonActivity.class);
	            startActivity(intent);            
			}
		});  
	}
	
	public void showMaintButton(View view)		
	{
		Button button = (Button) findViewById(R.id.buttonShowMaint);
		button.setOnClickListener(new View.OnClickListener() {    
			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ShowMaintenanceActivity.class);
	            startActivity(intent);            
			}
		});  
	}

	public void helpMessage(View view)
	{
		Button button = (Button) findViewById(R.id.helpButton);
		button.setOnClickListener(new View.OnClickListener() {    
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
				builder.setIcon(R.drawable.helpicon)
				.setTitle("Help!")
				.setMessage(helpMain)
				.setNeutralButton("OK", null);
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});  
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void onSaveInstanceState(Bundle outState)
	{
	    super.onSaveInstanceState(outState);
	    
	    mOdometerValue = mOdometer.getValue();	
	    outState.putInt(KEY_VALUE, mOdometerValue);
	}

	public void getCurrentMileage() {
		
	}

	public static int setCurrentMileage() {
		return mOdometer.getValue();
	}

	public void setFillupMileage() {

	}

	public void getNextMaintenance() {

	}
}
