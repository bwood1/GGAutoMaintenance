package com.example.ggcautomaintenance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {
	private MaintItemsTableDataSource datasource;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		datasource = new MaintItemsTableDataSource(this);
		datasource.open();
		datasource.dropMaintItemsTable();
		datasource.close();
		
		if(!checkDataBase())
			createMaintItemsTable();

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

	/**
	 * Method to display help popup
	 * @param view
	 */
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
	
	public static int getOldMileage() {
		return oldOdometer;
	}
	
	public static void setOldMileage()	{
		oldOdometer = getCurrentMileage();
	}

	public static int getCurrentMileage() {
		return currentOdometer;
	}

	public static void setCurrentMileage() {
		currentOdometer = mOdometer.getValue();
	}
	
	public static int getMilesDriven() {
		return getCurrentMileage() - getOldMileage();
	}

	public void setFillupMileage() {

	}

	public void getNextMaintenance() {

	}

	private void createMaintItemsTable() {
		//Create the maint items table and populate fields
		System.out.println("Creating Maint Items Table");
		datasource = new MaintItemsTableDataSource(this);
		datasource.open();
		
		datasource.addMaintenanceItem(1, "Battery", 70000, 60);
		datasource.addMaintenanceItem(2, "Brake Fluid", 30000, 24);
		datasource.addMaintenanceItem(3, "Brake Inspection", 15000, 12);
		datasource.addMaintenanceItem(4, "Cabin Air Filter", 15000, 18);
		datasource.addMaintenanceItem(5, "Engine Air Filter", 10000, 12);
		datasource.addMaintenanceItem(6, "Front Brake Pads", 50000, 36);
		datasource.addMaintenanceItem(7, "Oil Change", 3000, 4);
		datasource.addMaintenanceItem(8, "Power Steering Fluid", 60000, 48);
		datasource.addMaintenanceItem(9, "Radiator Fluid", 30000, 24);
		datasource.addMaintenanceItem(10, "Rear Brakes", 50000, 36);
		datasource.addMaintenanceItem(11, "Spark Plugs", 30000, 24);
		datasource.addMaintenanceItem(12, "State Inspection", 15000, 12);
		datasource.addMaintenanceItem(13, "Timing Belt", 60000, 48);
		datasource.addMaintenanceItem(14, "Tire Rotation", 10000, 12);
		datasource.addMaintenanceItem(15, "Transmission Fluid", 20000, 18);
		datasource.addMaintenanceItem(16, "Valve Adjustment", 30000, 24);
		datasource.addMaintenanceItem(17, "Wheel Alignment", 30000, 24);
		datasource.addMaintenanceItem(18, "Windshield Wiper", 15000, 12);
		datasource.addMaintenanceItem(19, "Accesory Belts", 40000, 48);
		datasource.addMaintenanceItem(20, "Idle Air Control", 30000, 24);
		datasource.addMaintenanceItem(21, "Brake Flush", 30000, 24);
		datasource.addMaintenanceItem(22, "Engine Thermostat", 30000, 36);
		
		datasource.close(); //DONT FORGET TO CLOSE
	}
	
	//checks to see if maint items has been created
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			MaintItemsTableDataSource db = null;
			checkDB = db.open();
			checkDB.close();
			System.out.println("Database exists");
			return true;
		} catch (NullPointerException e) {
			//database doesnt exist
			System.out.println("Database does not exist");
			return false;
		}	
	}
}
