package com.example.ggcautomaintenance;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class ShowMaintenanceActivity extends Activity {
	
	String helpMain = 	"* A list of Maintenance Items are displayed. By default the list is sorted by maintenance due next. \n" +
			"* The list can be sorted alphabetically by selecting Sort Alpha. \n" +
			"* Select Sort Next Due to go back. \n" +
			"* The list can be scrolled up and down by swiping up or down. \n +" +
			"* To see more information or record maintenance on a specific item select the item in the list.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_maintenance);
		// Show the Up button in the action bar.
		//setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	public void helpMessage(View view)
	{
		Button button = (Button) findViewById(R.id.helpButtonShowMaint);
		button.setOnClickListener(new View.OnClickListener() {    
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ShowMaintenanceActivity.this);
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
		getMenuInflater().inflate(R.menu.show_maintenance, menu);
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
