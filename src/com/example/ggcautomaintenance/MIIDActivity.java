package com.example.ggcautomaintenance;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MIIDActivity extends Activity {
	
	private String value;
	
	// Text for the help dialog
	String helpMain = 	"* Information is displayed about the specific maintenance item. \n" +
			"* To record maintenance performed select Record Maintenance. \n" +
			"* A popup will display. \n" +
			"* The current date and mileage can be accepted by selecting the Checkbox or a date and mileage can be entered. \n" +
			"* Select Record Service to save this record.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_miid);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    value = extras.getString("specificItem");
		}
		
		TextView maintIDDisplay = (TextView)findViewById(R.id.miidDisplay);
		maintIDDisplay.setText("" + value);
	}
	
	/**
	 * Method to display help popup
	 * @param view
	 */
	public void helpMessage(View view)
	{
		Button button = (Button) findViewById(R.id.helpButtonMIID);
		button.setOnClickListener(new View.OnClickListener() {    
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MIIDActivity.this);
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
