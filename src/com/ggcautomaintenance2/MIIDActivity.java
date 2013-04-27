package com.ggcautomaintenance2;

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
	public static CarMaintDataSource dataSource;
	//private String value;
	OptionSelectionPopup OSPopup;
	ChangeDefaultPopup CDPopup;

	MIID miid;

	// Text for the help dialog
	String helpMain = "* Information is displayed about the specific maintenance item.\n" +
			"* To record maintenance performed select Record Maintenance.\n" +
			"* A popup will display.\n" +
			"* The current date and mileage can be accepted by selecting the Checkbox or a date and mileage can be entered.\n" +
			"* Select Record Service to save this record.\n" +
			"* If you would like to change the mileage or month interval between maintenance press the default Maintenance button.\n" +
			"* Then fill in number of months, and number of miles and press Set.";

	Button helpButton;
	Button recMaintButton;
	Button ospRecordButton;
	Button setDefaultButton;

	int maintId;
	String maintType;
	String maintDesc;

	EditText inputDateField;
	EditText inputMileageField;

	EditText inputTimeInterval;
	EditText inputMileageInterval;

	Integer miles;
	String maintCompleteDate;
	String dueDate;
	DateFormat dateFormat;
	int newMileageDue;

	/**
	 * onCreate
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_miid);

		dataSource = new CarMaintDataSource(this);
		dataSource.open();

		helpButton = (Button) findViewById(R.id.helpButtonMIID);
		recMaintButton = (Button) findViewById(R.id.recordMaintButton);
		ospRecordButton = (Button) findViewById(R.id.recordMaintOSP);
		setDefaultButton = (Button) findViewById(R.id.setDefaultButton);

		Bundle extras = getIntent().getExtras();

		maintDesc = extras.getString("MaintDesc");
		maintId = extras.getInt("MaintId");

		miid = new MIID(maintId, dataSource);

		TextView textViewMaintName = (TextView)findViewById(R.id.textViewMaintName);
		textViewMaintName.setText(maintDesc);

		miid.setCurrentOdometer();

		TextView textViewMilesDrivenSinceService = (TextView)findViewById(R.id.textViewMilesDrivenSinceService);
		textViewMilesDrivenSinceService.setText("Miles Driven Since Service\n" + miid.getMilesSince());

		TextView textViewDateOfNextService = (TextView)findViewById(R.id.textViewDateOfNextService);
		textViewDateOfNextService.setText("Date of Next Service\n" + miid.getDateNextDue());

		TextView textViewMilesTillNextService = (TextView)findViewById(R.id.textViewMilesTillNextService);
		textViewMilesTillNextService.setText("Miles Until Next Service\n" + miid.getMilesTill(maintId));

		System.out.println("Miles till = " + miid.getMilesTill(maintId));

		TextView textViewDateofLastService = (TextView)findViewById(R.id.textViewDateofLastService);
		textViewDateofLastService.setText("Date of Last Service\n" + miid.getDateLastServ());

		TextView textViewMileageOfLastService = (TextView)findViewById(R.id.textViewMileageOfLastService);
		textViewMileageOfLastService.setText("Mileage of Last Service\n" + miid.getMilesLast());
	}

	@Override
	protected void onPause() {
		super.onPause();
		dataSource.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		dataSource.open();
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

	/**
	 * Fires the Option Selection Prompt when Record Maintenance Button is pressed
	 * @param view
	 */
	public void recordMaintButton(View view)
	{
		OSPopup = new OptionSelectionPopup(view.getContext());
		OSPopup.show(view);
		OSPopup.update();
	}

	/*
	 * Listener for record maintenance button on Option Selection Prompt
	 */
	public void record(View view) throws ParseException {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

		getTextFromFields();

		CheckBox useCurrentDateAndMiles = (CheckBox)OSPopup.getContentView().findViewById(R.id.useCurrentBox);

		if(useCurrentDateAndMiles.isChecked()){
			maintCompleteDate = dataSource.getCurrentDate();
			miles = dataSource.getMileage();
		}

		//calculate new due date and miles
		calculateNewDueDate();
		calculateNewDueMiles();

		//update the database
		dataSource.updateMaintRecord(maintId, maintCompleteDate, "car1", maintId, miles, 0.00,
				dueDate, newMileageDue);

		OSPopup.dismiss();
		view.invalidate();
	}

	/**
	 * Gets the values from the text fields and stores them as the variable miles, and maintCompleteDate
	 */
	private void getTextFromFields() {
		inputDateField = (EditText)OSPopup.getContentView().findViewById(R.id.inputDateField);
		inputMileageField = (EditText)OSPopup.getContentView().findViewById(R.id.inputMileageField);
		miles = Integer.valueOf(inputMileageField.getText().toString());
		maintCompleteDate = inputDateField.getText().toString();
	}

	/**
	 * Takes the date entered and adds the correct number of weeks to it
	 * @throws ParseException
	 */
	private void calculateNewDueDate() throws ParseException {
		//calculate the due date
		Date maintLastDoneDate = new Date();
		Calendar cal = Calendar.getInstance(); 
		maintLastDoneDate = dateFormat.parse(maintCompleteDate);	//when the maint was done
		cal.setTime(maintLastDoneDate);
		cal.add(Calendar.MONTH, dataSource.getTimeInterval(maintId));  //add time interval

		dueDate = "" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + 
				cal.get(Calendar.DAY_OF_MONTH);	//build string
	}

	/**
	 * takes the miles entered and adds the correct mileage interval to calculate new new mileage due
	 */
	private void calculateNewDueMiles() {
		//calculate the due mileage
		newMileageDue = miles +	dataSource.getMileageInterval(maintId);
	}

	/**
	 * method to dismiss popup on exit button 
	 * @param view
	 */
	public void dismissView (View view) {
		OSPopup.getContentView().findViewById(R.id.exitButton);
		OSPopup.dismiss();
	}

	/**
	 * method for autopopulating edittexts in the OSPopup
	 * @param view
	 */
	public void checkedBox(View view) {
		inputDateField = (EditText)OSPopup.getContentView().findViewById(R.id.inputDateField);
		inputMileageField = (EditText)OSPopup.getContentView().findViewById(R.id.inputMileageField);
		inputMileageField.setText("" + dataSource.getMileage());
		inputDateField.setText(dataSource.getCurrentDate());
	}

	/**
	 * onCreateOptionsMenu method
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.miid, menu);
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

	/**
	 * section for default change popup
	 * @param view
	 */
	public void setDefault(View view) {
		CDPopup = new ChangeDefaultPopup(view.getContext());
		CDPopup.show(view);
		CDPopup.update();
	}

	/**
	 * exit button
	 * @param view
	 */
	public void dismissDefView(View view) {
		CDPopup.getContentView().findViewById(R.id.exitButton);
		CDPopup.dismiss();
	}

	/**
	 * set default maintenance time and mileage difference
	 * @param view
	 */
	public void setDefaultButton(View view) {
		inputTimeInterval = (EditText)CDPopup.getContentView().findViewById(R.id.inputTimeInterval);
		inputMileageInterval = (EditText)CDPopup.getContentView().findViewById(R.id.inputMileageInterval);

		Integer timeInterval = Integer.valueOf(inputTimeInterval.getText().toString());
		Integer mileageInterval = Integer.valueOf(inputMileageInterval.getText().toString());		

		dataSource.setTimeInterval(maintId, timeInterval);
		dataSource.setMileageInterval(maintId, mileageInterval);

		CDPopup.dismiss();
		view.invalidate();
	}
}
