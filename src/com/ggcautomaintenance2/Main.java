package com.ggcautomaintenance2;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

public class Main extends Activity {
	private CarMaintDataSource dataSource;

	ListView listViewNext;
	ListItems[] nextItems;
	ListItemsArrayAdapter nextAdapter;

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

	private static Odometer fOdometer;

	Button mpgButton;
	Button showMButton;
	Button helpButton;
	Button enterButton;
	
	CheckBox fillup;
	CheckBox firstFillup;

	FirstTimeHelperPageOne Fpup;
	FirstTimeHelperPageTwo Spup;

	View popView;

	/**
	 * onCreate method
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		popView = findViewById(R.id.helpButton);

		CarMaintTableHelper myDbHelper = new CarMaintTableHelper(this);

		try {
			myDbHelper.createDataBase();

		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			myDbHelper.openDataBase();

		} catch(SQLException sqle) {
			throw sqle;
		}

		dataSource = new CarMaintDataSource(this);
		dataSource.open();

		//Set the views for the buttons
		mpgButton = (Button) findViewById(R.id.buttonCalcMPG);
		showMButton = (Button) findViewById(R.id.buttonShowMaint);
		helpButton = (Button) findViewById(R.id.helpButton);
		enterButton = (Button) findViewById(R.id.enterButton);

		mOdometer = (Odometer) findViewById(R.id.odometer);
		mOdometer.setValue(dataSource.getMileage()); //Sets the value of the odometer

		//parts for the upcoming list
		nextItems =  dataSource.getAllMaintenanceItemsDueDate();
		listViewNext = (ListView) findViewById(R.id.listUpcoming);
		nextAdapter = new ListItemsArrayAdapter(this, nextItems, dataSource.getMileage());
		listViewNext.setAdapter(nextAdapter);

		// Checks to see if this is the first time the app has been run
		if(isFirstTime())
		{
			new Handler().postDelayed(new Runnable() { //If it is the first time run the first time setup
				public void run() {
					displayFirstTimeSetup(popView);
				}
			}, 100);
		}
		dataSource.close();
	}

	/**
	 * onPause method
	 */
	@Override
	protected void onPause() {
		super.onPause();
		fillup = (CheckBox) findViewById(R.id.fillBox);
		fillup.setChecked(false);
		dataSource.close();
	}

	/**
	 * onResume method
	 */
	@Override
	protected void onResume() {
		super.onResume();
		dataSource.open();
		reloadUpcomingList();
	}
	
	/**
	 * Clears the upcoming maintenance items list and re-populates it.
	 */
	public void reloadUpcomingList() {
		listViewNext = null; //clear the list

		//parts for the upcoming list
		nextItems =  dataSource.getAllMaintenanceItemsDueDate();
		listViewNext = (ListView) findViewById(R.id.listUpcoming);
		nextAdapter = new ListItemsArrayAdapter(this, nextItems, dataSource.getMileage());
		listViewNext.setAdapter(nextAdapter);
	}

	/**
	 * enterButton Action Method
	 * @param view
	 */
	public void enterButton(View view) {
		dataSource = new CarMaintDataSource(this);
		dataSource.open();

		fillup = (CheckBox) findViewById(R.id.fillBox);

		if (fillup.isChecked()) {
			dataSource.setMileage(mOdometer.getValue());		
			dataSource.setCurrentMileage(mOdometer.getValue());
		}

		if (fillup.isChecked() == false) {
			dataSource.setMileage(mOdometer.getValue());
		}
		else{
		}
		
		reloadUpcomingList(); //reload the upcoming maintenance items
		dataSource.close();
	}

	/**
	 * mpgButton Action Method
	 * @param view
	 */
	public void mpgButton(View view) {
		onPause();
		Intent intent = new Intent(view.getContext(), MilesPerGallonActivity.class);
		startActivity(intent);            
	}

	/**
	 * showMaintButton Action Method
	 * @param view
	 */
	public void showMaintButton(View view) {
		Intent intent = new Intent(view.getContext(), ShowMaintenanceActivity.class);
		onPause();
		startActivity(intent);
	}

	/**
	 * Method to display help popup
	 * @param view
	 */
	public void helpMessage(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
		builder.setIcon(R.drawable.helpicon)
		.setTitle("Help!")
		.setMessage(helpMain)
		.setNeutralButton("OK", null);
		AlertDialog dialog = builder.create();
		dialog.show();
	} 

	/**
	 * onCreateOptionsMenu method
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * onSaveInstanceState method
	 */
	protected void onSaveInstanceState(Bundle outState)	{
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

	/**
	 * Returns miles driven
	 * @return milesDriven
	 */
	public static int getMilesDriven() {
		int milesDriven = getCurrentMileage() - getOldMileage();		

		return milesDriven;
	}

	/**
	 * Checks to see if this is the first time the app has been run
	 * @return - true is first time, false if not
	 */
	private boolean isFirstTime()
	{
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		boolean ranBefore = preferences.getBoolean("RanBefore", false);
		if (!ranBefore) {
			// first time
			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean("RanBefore", true);
			editor.commit();
		}
		return !ranBefore;
	}

	/**
	 * Shows the first time setup in a popup window
	 * @param view
	 */
	public void displayFirstTimeSetup(View view)
	{
		Fpup = new FirstTimeHelperPageOne(view.getContext());
		Fpup.show(view);
		Fpup.update();
	}

	/**
	 * closes the first screen of the first time setup 
	 * and opens the second screen
	 * @param view
	 */
	public void nextStepButton(View view)
	{
		//closes popup1 first
		Fpup.getContentView().findViewById(R.id.nextStep);
		Fpup.dismiss();

		//opens second popup
		Spup = new FirstTimeHelperPageTwo(view.getContext());
		Spup.show(view);
		Spup.update();
	}

	/**
	 * Enter button for the odometer on the first time setup
	 * @param view
	 */
	public void firstEnterButton(View view)
	{
		dataSource = new CarMaintDataSource(this);
		dataSource.open();
		firstFillup = (CheckBox) Fpup.getContentView().findViewById(R.id.firstFillBox);
		fOdometer = (Odometer) Fpup.getContentView().findViewById(R.id.firstOdometer);

		if (firstFillup.isChecked()) {
			dataSource.setMileage(fOdometer.getValue());
			dataSource.setCurrentMileage(fOdometer.getValue());
		}
		else{
			dataSource.setMileage(fOdometer.getValue());
		}
		dataSource.close();
		mOdometer.setValue(fOdometer.getValue());
	}

	/**
	 * Enter maintenance info button for the first time setup
	 * @param view
	 */
	public void enterInfoButton(View view) {
		Spup.getContentView().findViewById(R.id.enterInfoButton);
		Spup.dismiss();

		Intent intent = new Intent(view.getContext(), ShowMaintenanceActivity.class);
		startActivity(intent);
	}

	/**
	 * Use default values button for the first time setup
	 * @param view
	 * @throws ParseException
	 */
	public void setDefaultButton(View view) throws ParseException {
		dataSource.open();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

		String maintCompleteDate = dataSource.getCurrentDate();
		int miles = dataSource.getMileage();

		for(int i = 1; i < 23; i++) {
			Date maintLastDoneDate = new Date();
			Calendar cal = Calendar.getInstance(); 
			maintLastDoneDate = dateFormat.parse(maintCompleteDate);	//when the maint was done
			cal.setTime(maintLastDoneDate);
			cal.add(Calendar.MONTH, dataSource.getTimeInterval(i));  //add time interval

			String dueDate = "" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + 
					cal.get(Calendar.DAY_OF_MONTH);													//build string

			//calculate the due mileage
			int newMileageDue = dataSource.getOdometer(i) + dataSource.getMileageInterval(i);

			dataSource.updateMaintRecord(i, maintCompleteDate, "car1", i, miles, 0.00,
					dueDate, newMileageDue);
		}
		reloadUpcomingList();
		dataSource.close();
		Spup.dismiss();
	}
}