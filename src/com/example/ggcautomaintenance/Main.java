package com.example.ggcautomaintenance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {
	private CarMaintDataSource dataSource;

	// Text for the help dialog
	String helpMain = 	"* Enter the Current Date in the Odometer. \n" +
			"* Select the Fill-Up check box if this mileage is being entered when filling-up the tank. \n" +
			"* Select Enter to save the current mileage. \n" +
			"* Select Calculate MPG to go to the Calculator Screen. \n" +
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
		Log.d("Steve Printed This", "onCreate");

		dataSource = new CarMaintDataSource(this);
		dataSource.open();
		dataSource.dropMaintItemsTable();
		dataSource.close();

		mpgButton = (Button) findViewById(R.id.buttonCalcMPG);
		showMButton = (Button) findViewById(R.id.buttonShowMaint);
		helpButton = (Button) findViewById(R.id.helpButton);
		enterButton = (Button) findViewById(R.id.enterButton);

		if(!checkDataBase()) {
			dataSource.open();
			createMaintItemsTable();
			createMaintRecordsTable();
			createMPGTable();
			//createCarTable();
		}

		mOdometer = (Odometer) findViewById(R.id.odometer);

		if(savedInstanceState != null)
		{
			mOdometerValue = savedInstanceState.getInt(KEY_VALUE);
			mOdometer.setValue(mOdometerValue);
		}
	}	

	public void enterButton(View view)
	{
		setCurrentMileage();		
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
		//return mpgDataSource.getOldMileage();
		return oldOdometer;
	}

	/**
	 * sets the old odometer reading in the mpg table
	 */
	public static void setOldMileage()	{
		//mpgDataSource.setOldMileage(mpgDataSource.getCurrentMileage());
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
		//System.out.println("The odometer value is : " + 
		//		mOdometer.getValue());
		//mpgDataSource.setCurrentMileage(mOdometer.getValue());


		currentOdometer = mOdometer.getValue();
	}

	public static int getMilesDriven() {
		//mpgDataSource = new MPGTableDataSource(this);
		//mpgDataSource.open();
		//int milesDriven = mpgDataSource.getCurrentMileage() - 
		//		mpgDataSource.getOldMileage();
		//mpgDataSource.close();
		int milesDriven = getCurrentMileage() - getOldMileage();		

		return milesDriven;
	}

	public void setFillupMileage() {

	}

	public void getNextMaintenance() {

	}

	// Load the Maintenance items table data
	private void createMaintItemsTable() {
		Log.d("Steve Printed This", "Begin CreateMaintItemsTable");

		dataSource = new CarMaintDataSource(this);
		dataSource.open();

		dataSource.addMaintenanceItem(1, "Battery", 70000, 60);
		dataSource.addMaintenanceItem(2, "Brake Fluid", 30000, 24);
		dataSource.addMaintenanceItem(3, "Brake Inspection", 15000, 12);
		dataSource.addMaintenanceItem(4, "Cabin Air Filter", 15000, 18);
		dataSource.addMaintenanceItem(5, "Engine Air Filter", 10000, 12);
		dataSource.addMaintenanceItem(6, "Front Brake Pads", 50000, 36);
		dataSource.addMaintenanceItem(7, "Oil Change", 3000, 4);
		dataSource.addMaintenanceItem(8, "Power Steering Fluid", 60000, 48);
		dataSource.addMaintenanceItem(9, "Radiator Fluid", 30000, 24);
		dataSource.addMaintenanceItem(10, "Rear Brakes", 50000, 36);
		dataSource.addMaintenanceItem(11, "Spark Plugs", 30000, 24);
		dataSource.addMaintenanceItem(12, "State Inspection", 15000, 12);
		dataSource.addMaintenanceItem(13, "Timing Belt", 60000, 48);
		dataSource.addMaintenanceItem(14, "Tire Rotation", 10000, 12);
		dataSource.addMaintenanceItem(15, "Transmission Fluid", 20000, 18);
		dataSource.addMaintenanceItem(16, "Valve Adjustment", 30000, 24);
		dataSource.addMaintenanceItem(17, "Wheel Alignment", 30000, 24);
		dataSource.addMaintenanceItem(18, "Windshield Wiper", 15000, 12);
		dataSource.addMaintenanceItem(19, "Accesory Belts", 40000, 48);
		dataSource.addMaintenanceItem(20, "Idle Air Control", 30000, 24);
		dataSource.addMaintenanceItem(21, "Brake Flush", 30000, 24);
		dataSource.addMaintenanceItem(22, "Engine Thermostat", 30000, 36);
		
		dataSource.close();
		
		Log.d("Steve Printed This", "End CreateMaintItemsTable");
	}

	// Load the Maintenance Records table data
	private void createMaintRecordsTable(){
		Log.d("Steve Printed This", "Begin CreateMaintRecordsTable");;
		dataSource = new CarMaintDataSource(this);
		dataSource.open();
		dataSource.addMaintRecordToDatabase(1, "2008/1/1", "car1", 1, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(2, "2008/1/1", "car1", 2, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(3, "2008/1/1", "car1", 3, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(4, "2008/1/1", "car1", 4, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(5, "2008/1/1", "car1", 5, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(6, "2008/1/1", "car1", 6, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(7, "2008/1/1", "car1", 7, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(8, "2008/1/1", "car1", 8, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(9, "2008/1/1", "car1", 9, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(10, "2008/1/1", "car1", 10, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(11, "2008/1/1", "car1", 11, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(12, "2008/1/1", "car1", 12, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(13, "2008/1/1", "car1", 13, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(14, "2008/1/1", "car1", 14, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(15, "2008/1/1", "car1", 15, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(16, "2008/1/1", "car1", 16, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(17, "2008/1/1", "car1", 17, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(18, "2008/1/1", "car1", 18, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(19, "2008/1/1", "car1", 19, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(20, "2008/1/1", "car1", 20, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(21, "2008/1/1", "car1", 21, 0, 0.0, "2008/3/1", 1);
		dataSource.addMaintRecordToDatabase(22, "2008/1/1", "car1", 22, 0, 0.0, "2008/3/1", 1);
		dataSource.close();
		Log.d("Steve Printed This", "End CreateMaintRecordsTable");;
	}

	// Load the MPG table data
	private void createMPGTable() {
		Log.d("Steve Printed This", "Begin MPGTable");
		dataSource = new CarMaintDataSource(this);
		dataSource.open();
		dataSource.addMPGToDatabase(1, "", 0, 0, 0.0);
		dataSource.addMPGToDatabase(2, "", 0, 0, 0.0);
		
		dataSource.close();	
		Log.d("Steve Printed This", "End MPGTable");
	}

	/*	// Load the Car table data
	private void createCarTable(){

		dataSource = new CarMaintDataSource(this);
		dataSource.open();

		dataSource.close();
	}
	 */
	//checks to see if Maintenance Items Table has been created
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			CarMaintDataSource db = null;
			checkDB = db.open();
			checkDB.close();
			Log.d("Steve Printed This", "Database exists");
			return true;
		} catch (NullPointerException e) {
			//database doesn't exist
			Log.d("Steve Printed This", "Database does not exists");
			return false;
		}	
	}

	    
}
