package com.example.ggcautomaintenance;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class ShowMaintenanceActivity extends Activity {
	private MaintItemsTableDataSource datasource;

	// Text for the help dialog
	String helpMain = 	"* A list of Maintenance Items are displayed. By default the list is sorted by maintenance due next. \n" +
			"* The list can be sorted alphabetically by selecting Sort Alpha. \n" +
			"* Select Sort Next Due to go back. \n" +
			"* The list can be scrolled up and down by swiping up or down. \n +" +
			"* To see more information or record maintenance on a specific item select the item in the list.";
	
	Button helpButton;
	Button alphaButton;
	Button nextDueButton;
	
	ListView listView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_maintenance);
		
		helpButton = (Button) findViewById(R.id.helpButtonShowMaint);
		alphaButton = (Button) findViewById(R.id.alphaButton);
		nextDueButton = (Button) findViewById(R.id.nextDueButton);
		
		datasource = new MaintItemsTableDataSource(this);
		datasource.open();
		
		final MaintItems[] items =   datasource.getAllMaintenanceItemsAlphabetical();
		
		listView1 = (ListView) findViewById(R.id.listView1);
	    listView1.setAdapter(new MaintItemsArrayAdapter(this, items));
		listView1.setOnItemClickListener(new OnItemClickListener() {
			
			//@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				String miid = "Maintenance ID " + items[position].getMaintId() + " \n"
						+ items[position].getMaintDescription() + " \n"
						+ items[position].getMileageInterval() + " miles \n"
						+ items[position].getTimeInterval() + " weeks";
				Intent intent = new Intent(view.getContext(), MIIDActivity.class);
				intent.putExtra("specificItem", miid);
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
		AlertDialog.Builder builder = new AlertDialog.Builder(ShowMaintenanceActivity.this);
		builder.setIcon(R.drawable.helpicon)
		.setTitle("Help!")
		.setMessage(helpMain)
		.setNeutralButton("OK", null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void alphaButton(View view)
	{
		//alphaButton sort code goes here.
	}
	
	public void nextDueButton(View view)
	{
		//nextDueButton sort code goes here.
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
