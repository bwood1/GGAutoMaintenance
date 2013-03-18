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

		/**
		 * Adds a new maint record to the table
		 * @param maintRecordId - ID number
		 * @param maintCompleteDate - date maint was completed
		 * @param carName - name of car
		 * @param maintId - the maintId
		 * @param odometer - odometer from when maint was done
		 * @param cost - the cost of the maint
		 */
		public void addMaintRecordToDatabase(int maintRecordId, 
											String maintCompleteDate, 
											String carName,	int maintId, 
											int odometer, double cost) {
			//TODO stuff to add a new car to the database
			//create a ContentValues to hold the information for the maint record
			ContentValues values = new ContentValues();
			values.put(MaintRecordTableHelper.COLUMN_MAINT_RECORD_ID, maintRecordId);
			values.put(MaintRecordTableHelper.COLUMN_MAINT_COMPLETE_DATE, 
					maintCompleteDate);
			values.put(MaintRecordTableHelper.COLUMN_CAR_NAME, carName);
			values.put(MaintRecordTableHelper.COLUMN_MAINT_ID, maintId);
			values.put(MaintRecordTableHelper.COLUMN_ODOMETER, odometer);
			values.put(MaintRecordTableHelper.COLUMN_COST, cost);
			
			//add maint record to database
			database.insert(MaintRecordTableHelper.TABLE_MAINT_RECORDS_TABLE, 
					null, values);
			
		}
		
		//Get a single maint record from the database
		/**
		 * Will get a single MaintRecord from the database
		 * @param maintId - ID of the record to be returned
		 * @return - a MaintRecord
		 */
		public MaintRecords getMaintRecord(String maintId) {
			//TODO stuff to get a single maint record from the database
			List<MaintRecords> maintRecordsList = new ArrayList<MaintRecords>();
			String[] maintIdArray = new String[1];
			maintIdArray[0] = maintId.toString();
			
			//make a cursor to hold the data from the query
			Cursor cursor = database.query(MaintRecordTableHelper.TABLE_MAINT_RECORDS_TABLE,
					allColumns, MaintRecordTableHelper.COLUMN_MAINT_ID, 
					maintIdArray, null, null, null);
			//move to fisrt cursor
			cursor.moveToFirst();
			
			MaintRecords maintRecords = cursorToMaintRecords(cursor);
			cursor.close();
			
			return maintRecords;
		}
		
		//get all maint records from the database and put into an array list
		public List<MaintRecords> getAllMaintRecords() {
			List<MaintRecords> maintRecords = new ArrayList();
			//TODO stuff to get all of the maint records from the database
			return maintRecords;
		}
		
		//update a maint record
		public void updateMaintRecord(int maintRecordId, 
											String maintCompleteDate, 
											String carName,	int maintId, 
											int odometer, double cost) {
			String[] maintIdArray = new String[1];
			maintIdArray[0] = String.valueOf(maintId);
			
			//create a content values to hold the information for the updated record
			ContentValues values = new ContentValues();
			values.put(MaintRecordTableHelper.COLUMN_MAINT_RECORD_ID, maintRecordId);
			values.put(MaintRecordTableHelper.COLUMN_MAINT_COMPLETE_DATE, maintCompleteDate);
			values.put(MaintRecordTableHelper.COLUMN_CAR_NAME, carName);
			values.put(MaintRecordTableHelper.COLUMN_MAINT_ID, maintId);
			values.put(MaintRecordTableHelper.COLUMN_ODOMETER, odometer);
			values.put(MaintRecordTableHelper.COLUMN_COST, cost);
			
			//updates the row
			database.update(MaintRecordTableHelper.TABLE_MAINT_RECORDS_TABLE, 
					values, MaintRecordTableHelper.COLUMN_MAINT_ID, maintIdArray);
		}
													
		
		public MaintRecords cursorToMaintRecords(Cursor cursor) {
			MaintRecords maintRecords = new MaintRecords();
			maintRecords.setMaintRecordId(cursor.getInt(0));
			maintRecords.setMaintCompleteDate(cursor.getString(1));
			maintRecords.setCarName(cursor.getString(3));
			maintRecords.setMaintId(cursor.getInt(4));
			maintRecords.setOdometer(cursor.getInt(5));
			maintRecords.setCost(cursor.getDouble(6));
			return maintRecords;
		}

}
