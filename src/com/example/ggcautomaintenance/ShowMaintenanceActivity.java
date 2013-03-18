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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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

		ListView listView1 = (ListView) findViewById(R.id.listView1);

		MaintItems[] items = {
				new MaintItems(1, "Oil Change", 3000, 12),
				new MaintItems(2, "Air Filter", 12000, 104),
				new MaintItems(3, "Wiper Blades", 12000, 104),
				new MaintItems(4, "Tire Rotation", 5000, 24),
				new MaintItems(5, "Wheel Balance", 10000, 48),
				new MaintItems(6, "AntiFreeze Replacement", 12000, 52),
				new MaintItems(7, "Clean Windows", 1000, 4),
				new MaintItems(8, "Change Sparkplugs", 48000, 208),
				new MaintItems(9, "Wash Car", 3000, 12),
				new MaintItems(10, "Change Belts", 12000, 104),
		};

		ArrayAdapter<MaintItems> adapter = new ArrayAdapter<MaintItems>(this,
				android.R.layout.simple_list_item_1, items);

		listView1.setAdapter(adapter);
		listView1.setOnItemClickListener(new OnItemClickListener() {
			//@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

				String item = ((TextView)view).getText().toString();
				Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(view.getContext(), MIIDActivity.class);
	            startActivity(intent);
			}
		});
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
