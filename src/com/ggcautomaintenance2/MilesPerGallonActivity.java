package com.ggcautomaintenance2;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MilesPerGallonActivity extends Activity {	
	public static CarMaintDataSource dataSource;
	public static DecimalFormat df = new DecimalFormat("#.00");

	// Text for the help dialog
	String helpMain = 	"* For accurate MPG calculations, mileage must be enter when filling up and the gas tank must be completely filled.\n" +
			"* Screen will include mileage driven since last fill-up by default.\n" +
			"* You may change the mileage if you so choose.\n" +
			"* Enter the # of gallons put in tank.\n" +
			"* Select Calculate and the MPG will be displayed.";
	
	Button mpgCalcButton;
	Button helpButton;
	
	/**
	 * onCreate method
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_miles_per_gallon);
		
		mpgCalcButton = (Button) findViewById(R.id.calcMPGButton);
		helpButton = (Button) findViewById(R.id.helpButtonMPG);
		
		dataSource = new CarMaintDataSource(this);
		dataSource.open();	
		
		TextView text = (TextView) findViewById(R.id.currentMileageTF);		
		text.setText("" + getMilesDriven());
		
		dataSource.setFillupMileage(dataSource.getCurrentMileage());
	}
	
	/**
	 * Called when the user leaves this activity but doesnt close it
	 */
	@Override
	protected void onPause() {
		super.onPause();
		dataSource.close();  //close when leaving the activity
	}
	
	/**
	 * Called when the user returns to this screen after its already been created
	 */
	@Override
	protected void onResume() {
		super.onResume();
		dataSource.open();
	}
	
	/**
	 * Return miles driven
	 * @return milesDriven 
	 */
	private int getMilesDriven() {
		int milesDriven;
		milesDriven = dataSource.getCurrentMileage() - dataSource.getFillupMileage();
		return milesDriven;
	}

	/**
	 * Method for mpg Calc Button, initiates MPG calculation and displays results
	 * @param view
	 */
	public void mpgCalcButton(View view)		
	{
		float mpg = getMPG();
		TextView mpgDisplay = (TextView)findViewById(R.id.MPGDisplay);
		if (mpg == 0) {
			mpgDisplay.setText("Enter Miles Driven");
		}
		else if (mpg > 10000) {
			mpgDisplay.setText("Enter Gallons Filled");
		}
		else {
			mpgDisplay.setText("" + df.format(mpg) + " MPG");
		}		
	}

	/**
	 * takes the data entered into the text fields and parses to float. Then calculates
	 * the MPG and returns the value
	 * @return mpg (miles per gallon)
	 */
	public float getMPG() {
		float mpg;
		EditText editMilesDriven = (EditText)findViewById(R.id.currentMileageTF);
		EditText editGallonsFilled = (EditText)findViewById(R.id.numGallonsFilledDisplay);
		
		float milesDriven = Float.valueOf(editMilesDriven.getText().toString());
		float gallonsFilled = Float.valueOf(editGallonsFilled.getText().toString());
		
		if (milesDriven == 0 && gallonsFilled == 0) {
			mpg = 0;
			return mpg;
		}
		else {
			MPGCalculator mpgCalc = new MPGCalculator(milesDriven, gallonsFilled, dataSource);		
			mpg = mpgCalc.getMPG();
			return mpg;
		}		
	}

	/**
	 * Method to display help popup
	 * @param view
	 */
	public void helpMessage(View view)	{
		AlertDialog.Builder builder = new AlertDialog.Builder(MilesPerGallonActivity.this);
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
		getMenuInflater().inflate(R.menu.miles_per_gallon, menu);
		return true;
	}
	
	/**
	 * onOptionsItemsSelected method
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}