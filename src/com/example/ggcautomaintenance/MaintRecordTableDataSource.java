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
			MaintRecordTableHelper.COLUMN_COST,
			MaintRecordTableHelper.COLUMN_MAINT_DUE_DATE,
			MaintRecordTableHelper.COLUMN_MAINT_DUE_MILEAGE};

	//Constructor
	public MaintRecordTableDataSource (Context context) {
		maintRecordDbHelper = new MaintRecordTableHelper(context);
	}

	//Open database
	public SQLiteDatabase open() throws SQLException {
		database = maintRecordDbHelper.getWritableDatabase();
		return database;
	}

	//Close database
	public void close() {
		maintRecordDbHelper.close();
	}

	public void dropMaintRecordsTable() {
		System.out.println("Delete values in maint records");
		database.delete(MaintRecordTableHelper.TABLE_MAINT_RECORDS_TABLE, null, null);
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
			int odometer, double cost, 
			String maintDueDate, int maintDueMileage) {
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
		values.put(MaintRecordTableHelper.COLUMN_MAINT_DUE_DATE, maintDueDate);
		values.put(MaintRecordTableHelper.COLUMN_MAINT_DUE_MILEAGE, maintDueMileage);

		//		String string = "January 2, 2010";
		//		Date date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(string);
		//		System.out.println(date); // Sat Jan 02 00:00:00 BOT 2010

		//TODO delete this
		/*
		 * ignore this i dont think its going to work
		 */
		//		database.rawQuery("INSERT INTO " + MaintRecordTableHelper.TABLE_MAINT_RECORDS_TABLE + " (" + 
		//		MaintRecordTableHelper.COLUMN_MAINT_RECORD_ID + ", " + MaintRecordTableHelper.COLUMN_MAINT_COMPLETE_DATE + ", " + 
		//		MaintRecordTableHelper.COLUMN_CAR_NAME + ", " + MaintRecordTableHelper.COLUMN_MAINT_ID + ", " + 
		//		MaintRecordTableHelper.COLUMN_ODOMETER + ", " + MaintRecordTableHelper.COLUMN_COST + ", " + 
		//		MaintRecordTableHelper.COLUMN_MAINT_DUE_DATE + ", " + MaintRecordTableHelper.COLUMN_MAINT_DUE_MILEAGE + ") " +
		//				"VALUES(" + maintRecordId + ", ", 
		//		selectionArgs)

		//add maint record to database
		database.insert(MaintRecordTableHelper.TABLE_MAINT_RECORDS_TABLE, null, values);

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

	/**
	 * Updates the maintenance record of the given recordId
	 * @param maintRecordId - ID to be updated
	 * @param maintCompleteDate - date maint was completed (most likely the current day)
	 * @param carName - Name of the car maint was performed on
	 * @param maintId - The maintenance ID from the maintID table
	 * @param odometer - odometer reading from when maint was done
	 * @param cost - how much the maintenance cost
	 * @param maintDueDate - maintCompleteDate + MaintItemsTableHelper.COLUMN_TIME_INTERVAL
	 * @param maintDueMileage - odometer + MaintItemsTableHelper.COLUMN_MILEAGE_INTERVAL
	 */
	public void updateMaintRecord(int maintRecordId, 
			String maintCompleteDate, 
			String carName,	int maintId, 
			int odometer, double cost,
			String maintDueDate, int maintDueMileage) {
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
		values.put(MaintRecordTableHelper.COLUMN_MAINT_DUE_DATE, maintDueDate);
		values.put(MaintRecordTableHelper.COLUMN_MAINT_DUE_MILEAGE, maintDueMileage);

		//updates the row
		database.update(MaintRecordTableHelper.TABLE_MAINT_RECORDS_TABLE, 
				values, MaintRecordTableHelper.COLUMN_MAINT_ID, maintIdArray);
	}

	//gets the next maintenance date due for the specified maintid
	public String getNextMaintDueDate (int maintId) {
		String[] maintDueColumn = new String[1];
		maintDueColumn[0] = MaintRecordTableHelper.COLUMN_MAINT_DUE_DATE;
		String[] selectionArgs = new String[1];
		selectionArgs[0] = String.valueOf(maintId);
		Cursor cursor = database.query(MaintRecordTableHelper.TABLE_MAINT_RECORDS_TABLE, 
				maintDueColumn, MaintRecordTableHelper.COLUMN_MAINT_ID, selectionArgs, 
				null, null, null);
		cursor.moveToFirst();

		return cursorToString(cursor);
	}

	public String cursorToString(Cursor cursor) {
		String outputString = new String();
		outputString = cursor.getString(0);
		return outputString;
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
