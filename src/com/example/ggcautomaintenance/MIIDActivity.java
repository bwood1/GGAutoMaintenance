package com.example.ggcautomaintenance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MIIDActivity extends Activity {
	public static CarMaintDataSource carMaintDataSource;
	//private String value;
	OptionSelectionPopup OSPopup;
	ChangeDefaultPopup CDPopup;

	MIID miid;

	// Text for the help dialog
	String helpMain = "* Information is displayed about the specific maintenance item.\n" +
			"* To record maintenance performed select Record Maintenance.\n" +
			"* A popup will display.\n" +
			"* The current date and mileage can be accepted by selecting the Checkbox or a date and mileage can be entered.\n" +
			"* Select Record Service to save this record.";

	Button helpButton;
	Button recMaintButton;
	Button ospRecordButton;
	Button setDefaultButton;

	int maintId;
	String maintType;
	String maintDesc;

	EditText inputDateField;
	EditText inputMileageField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_miid);

		carMaintDataSource = new CarMaintDataSource(this);
		carMaintDataSource.open();

		helpButton = (Button) findViewById(R.id.helpButtonMIID);
		recMaintButton = (Button) findViewById(R.id.recordMaintButton);
		ospRecordButton = (Button) findViewById(R.id.recordMaintOSP);
		setDefaultButton = (Button) findViewById(R.id.setDefaultButton);

		Bundle extras = getIntent().getExtras();

		maintDesc = extras.getString("MaintDesc");
		maintId = extras.getInt("MaintId");

		miid = new MIID(maintId, carMaintDataSource);

		TextView textViewMaintName = (TextView)findViewById(R.id.textViewMaintName);
		textViewMaintName.setText(maintDesc);

		TextView textViewMilesDrivenSinceService = (TextView)findViewById(R.id.textViewMilesDrivenSinceService);
		textViewMilesDrivenSinceService.setText("Miles Driven Since Service\n" + miid.getMilesSince());

		TextView textViewDateOfNextService = (TextView)findViewById(R.id.textViewDateOfNextService);
		textViewDateOfNextService.setText("Date of Next Service\n" + miid.getDateNextDue());

		TextView textViewMilesTillNextService = (TextView)findViewById(R.id.textViewMilesTillNextService);
		textViewMilesTillNextService.setText("Miles Until Next Service\n" + miid.getMilesTill());

		TextView textViewDateofLastService = (TextView)findViewById(R.id.textViewDateofLastService);
		textViewDateofLastService.setText("Date of Last Service\n" + miid.getDateLastServ());

		TextView textViewMileageOfLastService = (TextView)findViewById(R.id.textViewMileageOfLastService);
		textViewMileageOfLastService.setText("Mileage of Last Service\n" + miid.getMilesLast());
	}

	/**
	 * Method to display help popup
	 * @param view
	 */
	public void helpMessage(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(MIIDActivity.this);
		builder.setIcon(R.drawable.helpicon)
		.setTitle("Help!")
		.setMessage(helpMain)
		.setNeutralButton("OK", null);
		AlertDialog dialog = builder.create();
		dialog.show(); 
	} 

	//Fires the Option Selection Prompt when Record Maintenance Button is pressed
	public void recordMaintButton(View view)
	{
		OSPopup = new OptionSelectionPopup(view.getContext());
		OSPopup.show(view);
		OSPopup.update();
	}

	/*
	 * Listener for record maintenance button on Option Selection Prompt
	 */
	public void record(View view) throws ParseException
	{	    	

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

		inputDateField = (EditText)OSPopup.getContentView().findViewById(R.id.inputDateField);
		inputMileageField = (EditText)OSPopup.getContentView().findViewById(R.id.inputMileageField);
		Integer miles = Integer.valueOf(inputMileageField.getText().toString());
		String maintCompleteDate = inputDateField.getText().toString();

		CheckBox useCurrentDateAndMiles = (CheckBox)OSPopup.getContentView().findViewById(R.id.useCurrentBox);
		System.out.println(useCurrentDateAndMiles);
		if(useCurrentDateAndMiles.isChecked()){
			maintCompleteDate = carMaintDataSource.getCurrentDate();
			miles = carMaintDataSource.getMileage();
		}

		//calculate the due date
		Date maintLastDoneDate = new Date();
		Calendar cal = Calendar.getInstance(); 
		maintLastDoneDate = dateFormat.parse(maintCompleteDate);	//when the maint was done
		cal.setTime(maintLastDoneDate);
		cal.add(Calendar.MONTH, carMaintDataSource.getTimeInterval(maintId));  //add time interval

		String dueDate = "" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + 
				cal.get(Calendar.DAY_OF_MONTH);															//build string
		Log.d("Brandon wants to know what the date is", dueDate);

		//calculate the due mileage
		int newMileageDue = carMaintDataSource.getOdometer(maintId) + carMaintDataSource.getMileageInterval(maintId) - miles;

		carMaintDataSource.updateMaintRecord(maintId, maintCompleteDate, "car1", maintId, miles, 0.00,
				dueDate, newMileageDue);
		OSPopup.dismiss();
		view.invalidate();
	}
	//method to dismiss popup on exit button 
	public void dismissView (View view) {
		
		OSPopup.getContentView().findViewById(R.id.exitButton);
		OSPopup.dismiss();
	}
	//method for autopopulating edittexts in the OSPopup
	public void checkedBox(View view) {
		inputDateField = (EditText)OSPopup.getContentView().findViewById(R.id.inputDateField);
		inputMileageField = (EditText)OSPopup.getContentView().findViewById(R.id.inputMileageField);
		inputMileageField.setText("" + carMaintDataSource.getMileage());
		inputDateField.setText(carMaintDataSource.getCurrentDate());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.miid, menu);
		return true;
	}

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
	
	//section for default change popup
	public void setDefault(View view)
	{
		CDPopup = new ChangeDefaultPopup(view.getContext());
		CDPopup.show(view);
		CDPopup.update();
	}
}
