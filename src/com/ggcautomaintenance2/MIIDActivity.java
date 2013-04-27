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
	DateFormat dateFormat;
	Date maintLastDoneDate = new Date();
	Calendar cal = Calendar.getInstance();

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

		loadText();
	}

	/**
	 * Called when the user leaves this activity
	 */
	@Override
	protected void onPause() {
		super.onPause();
		dataSource.close();
	}

	/**
	 * Called when the user returns to this activity after it has been created
	 */
	@Override
	protected void onResume() {
		super.onResume();
		loadText();
		dataSource.open();
	}
	
	/**
	 * loads the text on the screen
	 */
	private void loadText() {
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

		TextView textViewDateofLastService = (TextView)findViewById(R.id.textViewDateofLastService);
		textViewDateofLastService.setText("Date of Last Service\n" + miid.getDateLastServ());

		TextView textViewMileageOfLastService = (TextView)findViewById(R.id.textViewMileageOfLastService);
		textViewMileageOfLastService.setText("Mileage of Last Service\n" + miid.getMilesLast());
	}

	/**
	 * Method to display help popup
	 * @param view
	 */
	public void helpMessage(View view) {
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
	public void recordMaintButton(View view) {
		OSPopup = new OptionSelectionPopup(view.getContext());
		OSPopup.show(view);
		OSPopup.update();
	}

	
	/**
	 * Writes the data entered into the database
	 * @param view
	 * @throws ParseException
	 */
	public void record(View view) throws ParseException {
		getTextFromFields();

		CheckBox useCurrentDateAndMiles = (CheckBox)OSPopup.getContentView().findViewById(R.id.useCurrentBox);

		if(useCurrentDateAndMiles.isChecked()){
			maintCompleteDate = dataSource.getCurrentDate();
			miles = dataSource.getMileage();
		}

		//update the database
		dataSource.updateMaintRecord(maintId, maintCompleteDate, "car1", maintId, miles, 0.00,
				calculateNewDueDate(maintCompleteDate), calculateNewDueMiles(miles, dataSource.getMileageInterval(maintId)));

		loadText();
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
	 * @param completedDate - date the maintenance was completed
	 * @return - next due date
	 * @throws ParseException
	 */
	private String calculateNewDueDate(String completedDate) throws ParseException {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		
		maintLastDoneDate = dateFormat.parse(completedDate);	//when the maint was done
		cal.setTime(maintLastDoneDate);
		cal.add(Calendar.MONTH, (dataSource.getTimeInterval(maintId) + 1));  //add time interval

		String dueDate = "" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + 
				cal.get(Calendar.DAY_OF_MONTH);	//build string
		return dueDate;
	}

	/**
	 * takes the miles entered and adds the correct mileage interval to calculate new new mileage due
	 * @param milesEntered - the mileage entered in the record
	 * @param mileageInterval - the interval between maintenance
	 * @return
	 */
	private int calculateNewDueMiles(int milesEntered, int mileageInterval) {
		int newMileageDue = milesEntered + mileageInterval;
		
		return newMileageDue;
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
	 * @throws ParseException 
	 */
	public void setDefaultButton(View view) throws ParseException {
		inputTimeInterval = (EditText)CDPopup.getContentView().findViewById(R.id.inputTimeInterval);
		inputMileageInterval = (EditText)CDPopup.getContentView().findViewById(R.id.inputMileageInterval);

		Integer timeInterval = Integer.valueOf(inputTimeInterval.getText().toString());
		Integer mileageInterval = Integer.valueOf(inputMileageInterval.getText().toString());		

		dataSource.setTimeInterval(maintId, timeInterval);
		dataSource.setMileageInterval(maintId, mileageInterval);
		
		dataSource.updateMaintRecord(maintId, dataSource.getMaintCompleteDate(maintId), "car1", maintId, 
				dataSource.getMaintCompleteOdometer(maintId), 0.00, calculateNewDueDate(dataSource.getMaintCompleteDate(maintId)),
				calculateNewDueMiles(dataSource.getMaintCompleteOdometer(maintId), dataSource.getMileageInterval(maintId)));

		loadText();
		
		CDPopup.dismiss();
		view.invalidate();
	}
}