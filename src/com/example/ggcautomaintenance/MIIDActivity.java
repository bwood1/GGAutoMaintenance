package com.example.ggcautomaintenance;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MIIDActivity extends Activity {
	public static CarMaintDataSource carMaintDataSource;
	private String value;
	OptionSelectionPopup OSPopup;
	
	MIID miid;
	
	// Text for the help dialog
	String helpMain = 	"* Information is displayed about the specific maintenance item. \n" +
			"* To record maintenance performed select Record Maintenance. \n" +
			"* A popup will display. \n" +
			"* The current date and mileage can be accepted by selecting the Checkbox or a date and mileage can be entered. \n" +
			"* Select Record Service to save this record.";
	
	Button helpButton;
	Button recMaintButton;
	Button ospRecordButton;
	
	int maintId;
	String maintType;
	String maintDesc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_miid);
		
		carMaintDataSource = new CarMaintDataSource(this);
		carMaintDataSource.open();
		
		helpButton = (Button) findViewById(R.id.helpButtonMIID);
		recMaintButton = (Button) findViewById(R.id.recordMaintButton);
    	ospRecordButton = (Button) findViewById(R.id.recordMaintOSP);
		
    	Bundle extras = getIntent().getExtras();
		
		maintDesc = extras.getString("MaintDesc");
		maintId = extras.getInt("MaintId");
		Log.d("Steve Printed This", "" + maintId);
		
		miid = new MIID(maintId, carMaintDataSource);
		
		TextView textViewMaintName = (TextView)findViewById(R.id.textViewMaintName);
		textViewMaintName.setText(maintDesc);
		
		TextView textViewMilesDrivenSinceService = (TextView)findViewById(R.id.textViewMilesDrivenSinceService);
		textViewMilesDrivenSinceService.setText("Miles Driven Since Service \n" + miid.getMilesSince());
		
		TextView textViewDateOfNextService = (TextView)findViewById(R.id.textViewDateOfNextService);
		textViewDateOfNextService.setText("Date of Next Service \n" + miid.getDateNextDue());	//carMaintDataSource.getNextMaintDueDate(maintId));	
		
		TextView textViewMilesTillNextService = (TextView)findViewById(R.id.textViewMilesTillNextService);
		textViewMilesTillNextService.setText("Miles Until Next Service \n" + miid.getMilesTill());
		
		TextView textViewDateofLastService = (TextView)findViewById(R.id.textViewDateofLastService);
		textViewDateofLastService.setText("Date of Last Service \n" + miid.getDateLastServ());
		
		TextView textViewMileageOfLastService = (TextView)findViewById(R.id.textViewMileageOfLastService);
		textViewMileageOfLastService.setText("Mileage of Last Service \n" + miid.getMilesLast());
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
		
		/*Listener for record maintenance button on Option Selection Prompt
		 * 
		 ****  AS OF 3/18/13  ****
		 *Only closes prompt window
		 * 
		*/
	    public void record(View view)
	    {
			OSPopup.dismiss();
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
}
