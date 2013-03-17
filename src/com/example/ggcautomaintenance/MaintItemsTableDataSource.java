package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MaintItemsTableDataSource {
	
	//Database fields
		private SQLiteDatabase database;
		private MaintItemsTableHelper maintItemsDbHelper;
		private String[] allColumns = {
				MaintItemsTableHelper.COLUMN_MAINT_ID,
				MaintItemsTableHelper.COLUMN_MAINT_DESCRIPTION,
				MaintItemsTableHelper.COLUMN_MILEAGE_INTERVAL,
				MaintItemsTableHelper.COLUMN_TIME_INTERVAL};

		//Constructor
		public MaintItemsTableDataSource (Context context) {
			maintItemsDbHelper = new MaintItemsTableHelper(context);
		}

		//Open database
		public void open() throws SQLException {
			database = maintItemsDbHelper.getWritableDatabase();
		}

		//Close database
		public void close() {
			maintItemsDbHelper.close();
		}
		
		//add new maint items to table
		public MaintItems addNewMaintItem(int maintId, String maintDescription, 
												int mileageInterval, int timeInterval) {
			//TODO stuff to add new MaintItem to table
			return new MaintItems();
		}

}
