package com.ggcautomaintenance2;

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
	private CarMaintDataSource datasource;

	// Text for the help dialog
	String helpMain = 	"* A list of Maintenance Items are displayed. By default the list is sorted by maintenance due next.\n" +
			"* The list can be sorted alphabetically by selecting Sort Alpha.\n" +
			"* Select Sort Next Due to go back.\n" +
			"* The list can be scrolled up and down by swiping up or down.\n" +
			"* To see more information or record maintenance on a specific item select the item in the list.";
	
	Button helpButton;
	Button alphaButton;
	Button nextDueButton;
	
	ListView listViewNext;
	ListView listViewAlpha;
	
	ListItems[] alphaItems;
	ListItems[] nextItems;
	
	ListItemsArrayAdapter nextAdapter;
	ListItemsArrayAdapter alphaAdapter;
	
	/**
	 * onCreate
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_maintenance);
		
		helpButton = (Button) findViewById(R.id.helpButtonShowMaint);
		alphaButton = (Button) findViewById(R.id.alphaButton);
		nextDueButton = (Button) findViewById(R.id.nextDueButton);
		
		datasource = new CarMaintDataSource(this);
		datasource.open();
		
		alphaItems = datasource.getUpcomingMaintenance();
		
//		alphaItems = datasource.getAllMaintenanceItemsAlphabetical();
		nextItems =  datasource.getAllMaintenanceItemsDueDate();
		
		//initialize both sorted views
		listViewNext = (ListView) findViewById(R.id.listViewNext);
		listViewAlpha = (ListView) findViewById(R.id.listViewAlpha);
		
		nextAdapter = new ListItemsArrayAdapter(this, nextItems);
		alphaAdapter = new ListItemsArrayAdapter(this, alphaItems);
		
		alphaViewListViewClicker();
		nextViewListViewClicker();	
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
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
	
	/**
	 * alphaButton Action Method
	 * @param view
	 */
	public void alphaButton(View view)
	{
		//alphaButton sort code goes here.
		//visibility value "0" is visible view and value "2" is invisible and takes no space		
		listViewNext.setVisibility(View.GONE);
		listViewNext.refreshDrawableState();
		listViewAlpha.setVisibility(View.VISIBLE);
		listViewAlpha.refreshDrawableState();
	}
	
	/**
	 * nextDueButton Action Method
	 * @param view
	 */
	public void nextDueButton(View view)
	{
		//nextDueButton sort code goes here.
		//visibility value "0" is visible view and value "2" is invisible and takes no space		
		listViewAlpha.setVisibility(View.GONE);
		listViewAlpha.refreshDrawableState();
		listViewNext.setVisibility(View.VISIBLE);
		listViewNext.refreshDrawableState();
	}
	
	/**
	 * alphViewListClicker Action Method
	 */
	void alphaViewListViewClicker()
	{
		listViewAlpha.setAdapter(alphaAdapter);
		listViewAlpha.setOnItemClickListener(new OnItemClickListener() {
			
			//@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				Intent intent = new Intent(view.getContext(), MIIDActivity.class);
				intent.putExtra("MaintId", alphaItems[position].getMaintId());
				intent.putExtra("MaintDesc", alphaItems[position].getMaintDescription());
	            startActivity(intent);
	            finish();
			}
		});
	}
	
	/**
	 * listener for the click
	 */
	void nextViewListViewClicker()
	{
		listViewNext.setAdapter(nextAdapter);
		listViewNext.setOnItemClickListener(new OnItemClickListener() {
			
			//@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				Intent intent = new Intent(view.getContext(), MIIDActivity.class);
				intent.putExtra("MaintId", nextItems[position].getMaintId());
				intent.putExtra("MaintDesc", nextItems[position].getMaintDescription());
	            startActivity(intent);
	            finish();
			}
		});
	}
	
	/**
	 * onCreateOptionsMenu method
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_maintenance, menu);
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
