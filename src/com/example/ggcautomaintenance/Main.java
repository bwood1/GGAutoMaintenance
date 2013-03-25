package com.example.ggcautomaintenance;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Main extends Activity {
	private CarMaintDataSource dataSource;
	private CarMaintTableHelper maintHelper;

	// Text for the help dialog
	String helpMain = "* Enter the Current Date in the Odometer.\n" +
			"* Select the Fill-Up check box if this mileage is being entered when filling-up the tank.\n" +
			"* Select Enter to save the current mileage.\n" +
			"* Select Calculate MPG to go to the Calculator Screen.\n" +
			"* Select Show Maintenance to go to the Maintenance Items List screen.";

	private static final String KEY_VALUE = "OdometerValue";
	private static Odometer mOdometer;
	private int mOdometerValue;
	private static int oldOdometer;
	private static int currentOdometer;

	Button mpgButton;
	Button showMButton;
	Button helpButton;
	Button enterButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		CarMaintTableHelper myDbHelper = new CarMaintTableHelper(this);

		try {
			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");
		}

		try {
			myDbHelper.openDataBase();

		}catch(SQLException sqle){

			throw sqle;
		}

		dataSource = new CarMaintDataSource(this);
		dataSource.open();

		mpgButton = (Button) findViewById(R.id.buttonCalcMPG);
		showMButton = (Button) findViewById(R.id.buttonShowMaint);
		helpButton = (Button) findViewById(R.id.helpButton);
		enterButton = (Button) findViewById(R.id.enterButton);

		mOdometer = (Odometer) findViewById(R.id.odometer);

		mOdometer.setValue(dataSource.getMileage());
	}	

	public void enterButton(View view)
	{
		CheckBox fillup = (CheckBox) findViewById(R.id.fillBox);
		if (fillup.isChecked()) {
			dataSource = new CarMaintDataSource(this);
			dataSource.open();
			dataSource.setCurrentMileage(mOdometer.getValue());	
			dataSource.close();
		}
		else{
			dataSource = new CarMaintDataSource(this);
			dataSource.open();
			dataSource.setMileage(mOdometer.getValue());	
			dataSource.close();
		}
	}

	public void mpgButton(View view)		
	{
		Intent intent = new Intent(view.getContext(), MilesPerGallonActivity.class);
		startActivity(intent);            
	}

	public void showMaintButton(View view)		
	{
		Intent intent = new Intent(view.getContext(), ShowMaintenanceActivity.class);
		startActivity(intent);
	}

	/**
	 * Method to display help popup
	 * @param view
	 */
	public void helpMessage(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
		builder.setIcon(R.drawable.helpicon)
		.setTitle("Help!")
		.setMessage(helpMain)
		.setNeutralButton("OK", null);
		AlertDialog dialog = builder.create();
		dialog.show();  
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
	/**
	 * Gets the old mileage from the mpg table
	 * @return - the old mileage
	 */
	public static int getOldMileage() {
		return oldOdometer;
	}

	/**
	 * sets the old odometer reading in the mpg table
	 */
	public static void setOldMileage()	{
		oldOdometer = getCurrentMileage();
	}

	/**
	 * Gets the current mileage from the MPG Table
	 * @return - current mileage
	 */
	public static int getCurrentMileage() {
		//return mpgDataSource.getCurrentMileage();

		return currentOdometer;
	}

	/**
	 * Sets the database according to the odometer dial on 
	 * the home screen
	 */
	public static void setCurrentMileage() {

		currentOdometer = mOdometer.getValue();
	}

	public static int getMilesDriven() {
		int milesDriven = getCurrentMileage() - getOldMileage();		

		return milesDriven;
	}

	public void setFillupMileage() {

	}

	public void getNextMaintenance() {

	}


}
