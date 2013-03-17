package com.example.ggcautomaintenance;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MaintRecordTableDataSource {
	
	//Database fields
		private SQLiteDatabase database;
		private MaintRecordTableHelper maintRecordDbHelper;
		private String[] allColumns = {
					MaintRecordTableHelper.COLUMN_MAINT_RECORD_ID,
					MaintRecordTableHelper.COLUMN_MAINT_COMPLETE_DATE,
					MaintRecordTableHelper.COLUMN_CAR_NAME,
					MaintRecordTableHelper.COLUMN_MAINT_ID,
					MaintRecordTableHelper.COLUMN_ODOMETER,
					MaintRecordTableHelper.COLUMN_COST};
		
		//Constructor
		public MaintRecordTableDataSource (Context context) {
			maintRecordDbHelper = new MaintRecordTableHelper(context);
		}
		
		//Open database
		public void open() throws SQLException {
			database = maintRecordDbHelper.getWritableDatabase();
		}

		//Close database
		public void close() {
			maintRecordDbHelper.close();
		}

		//Create a new maint record
		public MaintRecords createMaintRecord(int maintRecordId, String maintCompleteDate, String carName,
												int maintId, int odometer, double cost) {
			//TODO stuff to add a new car to the database
			return new MaintRecords();
		}
		
		//Get a single maint record from the database
		public MaintRecords getMaintRecord(String maintRecordId /*maint record to pull*/) {
			//TODO stuff to get a single maint record from the database
			return null;
		}
		
		//get all maint records from the database and put into an array list
		public List<MaintRecords> getAllMaintRecords() {
			List<MaintRecords> maintRecords = new ArrayList();
			//TODO stuff to get all of the maint records from the database
			return maintRecords;
		}

}
