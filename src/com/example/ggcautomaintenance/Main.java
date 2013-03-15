package com.example.ggcautomaintenance;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {
	
	private Button mGetHelpButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mGetHelpButton = (Button) findViewById(R.id.button1);
		
		mGetHelpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// do something
				AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
				builder.setTitle("Help!")
				    .setMessage("This is the help text")
				    .setNeutralButton("OK", null);

				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
}
	
	
	public void getCurrentMileage() {
		
	}
	
	public void setCurrentMileage() {
		
	}
	
	public void setFillupMileage() {
		
	}
	
	public void getNextMaintenance() {
		
	}
}
