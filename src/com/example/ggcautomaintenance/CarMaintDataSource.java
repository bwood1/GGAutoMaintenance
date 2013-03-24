package com.example.ggcautomaintenance;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CarMaintDataSource {

	// Database fields
	private SQLiteDatabase db;
	private CarMaintTableHelper carMaintTblHelper;

	// Constructor
	public CarMaintDataSource (Context context) {
		carMaintTblHelper = new CarMaintTableHelper(context);
	}

	// Open database
	public SQLiteDatabase open() throws SQLException {
		db = carMaintTblHelper.getWritableDatabase();
		return db;
	}

	// Close database
	public void close() {
		carMaintTblHelper.close();
	}

	/**
	 * drop tables
	 */
	public void dropMaintItemsTable() {
		db.delete(CarMaintTableHelper.TABLE_MAINT_ITEMS, null, null);
		db.delete(CarMaintTableHelper.TABLE_MAINT_RECORDS, null, null);
		db.delete(CarMaintTableHelper.TABLE_MPG, null, null);
	}

	/***************************************************************************************************************
	 ******************************** begin Maintenance Items SECTION ***********************************************/

	// Add new Maintenance Items to table
	/**
	 * does nothing
	 * @param maintId
	 * @param maintDescription
	 * @param mileageInterval 
	 * @param timeInterval
	 * @return
	 */
	public MaintItems addNewMaintItem(int maintId, String maintDescription, 
			int mileageInterval, int timeInterval) {

		return new MaintItems();
	}

	// Adding new Maintenance Items
	/**
	 * adds a new maint item to the database
	 * @param maintId
	 * @param maintDesc
	 * @param mileageInterval
	 * @param timeInterval
	 */
	public void addMaintenanceItem(int maintId, String maintDesc, int mileageInterval, int timeInterval) {

		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MI_MAINT_ID, maintId);
		values.put(CarMaintTableHelper.MI_MAINT_DESCRIPTION, maintDesc);
		values.put(CarMaintTableHelper.MI_MILEAGE_INTERVAL, mileageInterval);
		values.put(CarMaintTableHelper.MI_TIME_INTERVAL, timeInterval);

		// Inserting Row
		db.insert(CarMaintTableHelper.TABLE_MAINT_ITEMS, null, values);
	}

	// Getting single maintenance item
	/**
	 * returns a single maint item from the db
	 * @param id - id of the maint item to be returned
	 * @return
	 */
	public MaintItems getMaintenanceItem(int id) {

		Cursor cursor = db.query(CarMaintTableHelper.TABLE_MAINT_ITEMS, 
				new String[] { 
				CarMaintTableHelper.MI_MAINT_ID,
				CarMaintTableHelper.MI_MAINT_DESCRIPTION, 
				CarMaintTableHelper.MI_MILEAGE_INTERVAL, 
				CarMaintTableHelper.MI_TIME_INTERVAL},

				CarMaintTableHelper.MI_MAINT_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		MaintItems maintItem = new MaintItems(
				Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), 
				Integer.parseInt(cursor.getString(2)), 
				Integer.parseInt(cursor.getString(3)));

		return maintItem;
	}

	// Getting All Maintenance items sorted alphabetically
	/**
	 * gets all maint items sorted alphabetically
	 * @return
	 */
	public MaintItems[] getAllMaintenanceItemsAlphabetical() {
		MaintItems[] maintItemsArray = new MaintItems[22/*need to make into a variable if scope changes*/];

		// Select All Query
		String selectQuery = "SELECT MaintID, MaintDescription, " +
				"MileageInterval, TimeInterval "/*maintrecord.MaintDueDate */ +
				"FROM " + CarMaintTableHelper.TABLE_MAINT_ITEMS +
				" ORDER BY 2";

		Cursor cursor = db.rawQuery(selectQuery, null);

		int i = 0;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				MaintItems maintItem = new MaintItems();
				maintItem.setMaintId(Integer.parseInt(cursor.getString(0)));
				maintItem.setMaintDescription(cursor.getString(1));
				maintItem.setMileageInterval(Integer.parseInt(cursor.getString(2)));
				maintItem.setTimeInterval(Integer.parseInt(cursor.getString(3)));
				maintItemsArray[i] = maintItem;
				i++;
			} while (cursor.moveToNext());
		}

		// return Maintenance Items Array
		return maintItemsArray;
	}

	// Getting All Maintenance Items sorted by due date
	/**
	 * gets all maint items sorted by due date
	 * @return
	 */
	public MaintItems[] getAllMaintenanceItemsDueDate() {
		MaintItems[] maintItemsArray = new MaintItems[22/*need to make into a variable if scope changes*/];

		// Select All Query
		String selectQuery = "SELECT MaintID, MaintDescription, " +
				"MileageInterval, TimeInterval " /*maintrecord.MaintDueDate */ +
				"FROM " + CarMaintTableHelper.TABLE_MAINT_ITEMS  +
				" ORDER BY 4";

		Cursor cursor = db.rawQuery(selectQuery, null);

		int i = 0;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				MaintItems maintItem = new MaintItems();
				maintItem.setMaintId(Integer.parseInt(cursor.getString(0)));
				maintItem.setMaintDescription(cursor.getString(1));
				maintItem.setMileageInterval(Integer.parseInt(cursor.getString(2)));
				maintItem.setTimeInterval(Integer.parseInt(cursor.getString(3)));
				maintItemsArray[i] = maintItem;
				i++;
			} while (cursor.moveToNext());
		}

		// return Maintenance Items Array
		return maintItemsArray;
	}

	// Getting Maintenance Items Count
	/**
	 * gets the count of the maint items
	 * @return
	 */
	public int getMaintenanceItemsCount() {
		String countQuery = "SELECT  * FROM " + CarMaintTableHelper.TABLE_MAINT_ITEMS;
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	// Updating single Maintenance Item
	/**
	 * updates a row in the maint items table
	 * @param maintItem - maint item number (used to limit the query to one line)
	 * @return
	 */
	public int updateMaintRecord(MaintItems maintItem) {

		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MI_MAINT_ID, maintItem.getMaintId());
		values.put(CarMaintTableHelper.MI_MAINT_DESCRIPTION, maintItem.getMaintDescription());
		values.put(CarMaintTableHelper.MI_MILEAGE_INTERVAL, maintItem.getMileageInterval());
		values.put(CarMaintTableHelper.MI_TIME_INTERVAL, maintItem.getTimeInterval());

		// updating row
		return db.update(CarMaintTableHelper.TABLE_MAINT_ITEMS, values, 
				CarMaintTableHelper.MI_MAINT_ID + " = ?",
				new String[] { String.valueOf(maintItem.getMaintId())});
	}

	// Deleting single Maintenance Record
	public void deleteMaintRecord(MaintItems maintItem) {
		db.delete(CarMaintTableHelper.TABLE_MAINT_ITEMS, 
				CarMaintTableHelper.MI_MAINT_ID + " = ?",
				new String[] { String.valueOf(maintItem.getMaintId()) });
	}

	/*********************************************************************************************************
	 ********************************** begin Maintenance Records SECTION *************************************/

	/**
	 * Adds a new maint record to the table
	 * 
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
		//create a ContentValues to hold the information for the maint record
		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MR_MAINT_RECORD_ID, maintRecordId);
		values.put(CarMaintTableHelper.MR_MAINT_COMPLETE_DATE, maintCompleteDate);
		values.put(CarMaintTableHelper.MR_CAR_NAME, carName);
		values.put(CarMaintTableHelper.MR_MAINT_ID, maintId);
		values.put(CarMaintTableHelper.MR_ODOMETER, odometer);
		values.put(CarMaintTableHelper.MR_COST, cost);
		values.put(CarMaintTableHelper.MR_MAINT_DUE_DATE, maintDueDate);
		values.put(CarMaintTableHelper.MR_MAINT_DUE_MILEAGE, maintDueMileage);

		//add maint record to database
		db.insert(CarMaintTableHelper.TABLE_MAINT_RECORDS, null, values);
		Log.d("Steve Printed This", "addMaintRecordToDatabase");
	}

	//Get a single maint record from the database
	/**
	 * Will get a single MaintRecord from the database
	 * @param maintId - ID of the record to be returned
	 * @return - a MaintRecord
	 */
	public MaintRecords getMaintRecord(String maintId) {
		List<MaintRecords> maintRecordsList = new ArrayList<MaintRecords>();
		String[] maintIdArray = new String[1];
		maintIdArray[0] = maintId.toString();
		String[] allColumns = {
				CarMaintTableHelper.MR_MAINT_RECORD_ID,
				CarMaintTableHelper.MR_MAINT_COMPLETE_DATE,
				CarMaintTableHelper.MR_CAR_NAME,
				CarMaintTableHelper.MR_MAINT_ID,
				CarMaintTableHelper.MR_ODOMETER,
				CarMaintTableHelper.MR_COST,
				CarMaintTableHelper.MR_MAINT_DUE_DATE,
				CarMaintTableHelper.MR_MAINT_DUE_MILEAGE};

		//make a cursor to hold the data from the query
		Cursor cursor = db.query(CarMaintTableHelper.TABLE_MAINT_RECORDS,
				allColumns, CarMaintTableHelper.MR_MAINT_ID, 
				maintIdArray, null, null, null);
		//move to first cursor
		cursor.moveToFirst();

		MaintRecords maintRecords = cursorToMaintRecords(cursor);
		cursor.close();

		return maintRecords;
	}

	//get all maint records from the database and put into an array list
	/**
	 * returns a list of all maint records
	 * @return
	 */
	public List<MaintRecords> getAllMaintRecords() {
		List<MaintRecords> maintRecords = new ArrayList();
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
		values.put(CarMaintTableHelper.MR_MAINT_RECORD_ID, maintRecordId);
		values.put(CarMaintTableHelper.MR_MAINT_COMPLETE_DATE, maintCompleteDate);
		values.put(CarMaintTableHelper.MR_CAR_NAME, carName);
		values.put(CarMaintTableHelper.MR_MAINT_ID, maintId);
		values.put(CarMaintTableHelper.MR_ODOMETER, odometer);
		values.put(CarMaintTableHelper.MR_COST, cost);
		values.put(CarMaintTableHelper.MR_MAINT_DUE_DATE, maintDueDate);
		values.put(CarMaintTableHelper.MR_MAINT_DUE_MILEAGE, maintDueMileage);

		//updates the row
		db.update(CarMaintTableHelper.TABLE_MAINT_RECORDS, 
				values, CarMaintTableHelper.MR_MAINT_ID, maintIdArray);
	}

	//gets the next maintenance date due for the specified maintid
	/**
	 * gets the due date of the maint id selected
	 * @param maintId
	 * @return
	 */
	public String getNextMaintDueDate (int maintId) {
		String query = "SELECT MaintDueDate FROM MaintRecord WHERE MaintId IS " + maintId;
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();

		return cursorToString(cursor);
	}

	/**
	 * gets the due date of the maint id selected
	 * @param maintId
	 * @return
	 */
	public int getMaintDueMileage(int maintId)
	{
		String query = "SELECT maintduemileage FROM maintrecord WHERE MaintId IS " +
				maintId;

		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();

		int odometer;
		odometer = cursorToInt(cursor);
		return odometer;
	}

	//gets the maintenance date completed for the specified maintid
	/**
	 * gett the date completed for the maint id selected
	 * @param maintId
	 * @return
	 */
	public String getNextMaintCompleteDate (int maintId) {
		String query = "SELECT maintCompleteDate FROM maintrecord WHERE MaintId IS " + maintId;

		Cursor test = db.rawQuery(query, null);
		test.moveToFirst();

		String completeDate = new String();
		completeDate = cursorToString(test);
		return completeDate;
	}

	/**
	 * gets the odometer reading from when the maintenance was completed
	 */
	public int getOdometer(int maintId)
	{
		String query = "SELECT odometer FROM maintrecord WHERE MaintId IS " + maintId;

		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();

		int odometer;
		odometer = cursorToInt(cursor);
		return odometer;
	}

	/**
	 * converts a cursor into a maint item
	 * @param cursor
	 * @return
	 */
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



	/*********************************************************************************************************
	 **************************************** begin MPG SECTION ***********************************************/

	// Add new MPG to table
	/**
	 * does nothing
	 * @param fillNumber
	 * @param carName
	 * @param oldOdometer
	 * @param gallons
	 * @param costPerGallon
	 * @return
	 */
	public MPG addNewMPG(int fillNumber, String carName, int oldOdometer, 
			int gallons, double costPerGallon) {

		return new MPG();
	}

	/**
	 * Add a new mpg to the table
	 * @param fillNumber
	 * @param carName
	 * @param oldOdometer
	 * @param gallons
	 * @param costPerGallon
	 */
	public void addMPGToDatabase(int fillNumber, String carName, int oldOdometer, 
			int gallons, double costPerGallon) {

		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MPG_FILL_NUMBER, fillNumber);
		values.put(CarMaintTableHelper.MPG_CAR_NAME, carName);
		values.put(CarMaintTableHelper.MPG_ODOMETER, oldOdometer);
		values.put(CarMaintTableHelper.MPG_GALLONS, gallons);
		values.put(CarMaintTableHelper.MPG_COST_PER_GALLON, costPerGallon);

		db.insert(CarMaintTableHelper.TABLE_MPG, null, values);
	}

	/**
	 * Adds a new fill up to the table
	 * @param fillNumber - always will be 1
	 * @param carName - name of car
	 * @param odometer - odometer reading
	 * @param gallons - number of gallons put in(optional)
	 * @param costPerGal - cost per gal (optional)
	 */
	public void addNewFill(int fillNumber, String carName, 
			int odometer, int gallons, double costPerGal) {
		//Create a content values to hole all the info
		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MPG_FILL_NUMBER, fillNumber);
		values.put(CarMaintTableHelper.MPG_CAR_NAME, carName);
		values.put(CarMaintTableHelper.MPG_ODOMETER, odometer);
		values.put(CarMaintTableHelper.MPG_GALLONS, gallons);
		values.put(CarMaintTableHelper.MPG_COST_PER_GALLON, costPerGal);

		//replace the first entry of the table with new fill up
		db.insert(CarMaintTableHelper.TABLE_MPG, null, values);

	}

	//Get old MPG
	/**
	 * gets the old mileage (from the last fill up or row 1 on the table)
	 * @return
	 */
	public int getOldMileage() {

		String[] columns = new String[1];
		columns[0] = CarMaintTableHelper.MPG_ODOMETER;

		String[] whereArgs = new String[1];
		whereArgs[0] = "1";

		//Create a cursor to hold the data 
		//till we can convert to int
		Cursor cursor = db.query(CarMaintTableHelper.TABLE_MPG, 
				columns, CarMaintTableHelper.MPG_FILL_NUMBER, 
				whereArgs, null, null, null);

		cursor.moveToFirst();
		int integer = cursorToInt(cursor);
		cursor.close();

		return integer;
	}

	/**
	 * sets the old odomter (ie current fill up or row 1 on the table
	 * @param oldOdometer
	 */
	public void setOldMileage(int oldOdometer) {
		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MPG_ODOMETER, oldOdometer);

		String[] whereArgs = new String[1];
		whereArgs[0] = "1";
		//update table
		db.update(CarMaintTableHelper.TABLE_MPG, 
				values, CarMaintTableHelper.MPG_FILL_NUMBER ,
				whereArgs);
	}

	/**
	 * sets the current mileage (row 2 on the table)
	 * @param odometer
	 */
	public void setCurrentMileage(int odometer) {
		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MPG_ODOMETER, odometer);

		String[] whereArgs = new String[1];
		whereArgs[0] = "2";

		//update table
		db.update(CarMaintTableHelper.TABLE_MPG, 
				values, CarMaintTableHelper.MPG_FILL_NUMBER ,
				whereArgs);
		System.out.println("Current Mileage has been updated");
	}

	//Get current mileage
	/**
	 * gets the current milage (row 2 on the table)
	 * @return
	 */
	public int getCurrentMileage() {
		String[] columns = new String[1];
		columns[0] = String.valueOf(CarMaintTableHelper.MPG_ODOMETER);

		String[] args = new String[1];
		args[0] = "2";

		Cursor cursor = db.query(CarMaintTableHelper.TABLE_MPG, columns, 
				CarMaintTableHelper.MPG_FILL_NUMBER, args, null, null, null);

		cursor.moveToFirst();
		int currentMileage = cursorToInt(cursor);

		return currentMileage;
	}



	//will update the old MPG with the new data
	/**
	 * updates the old mpg with the new data
	 * @param fillNumber
	 * @param carName
	 * @param oldOdometer
	 * @param gallons
	 * @param costPerGallon
	 */
	public void updateMPG(int fillNumber, String carName, int oldOdometer,
			int gallons, double costPerGallon) {

		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MPG_FILL_NUMBER, fillNumber);
		values.put(CarMaintTableHelper.MPG_CAR_NAME, carName);
		values.put(CarMaintTableHelper.MPG_ODOMETER, oldOdometer);
		values.put(CarMaintTableHelper.MPG_GALLONS, gallons);
		values.put(CarMaintTableHelper.MPG_COST_PER_GALLON, costPerGallon);

		db.update(CarMaintTableHelper.TABLE_MPG, values, null, null);
	}

	//When you do a query it returns a cursor. 
	//must convert to Integer
	private int cursorToInt(Cursor cursor) {
		Integer integer = new Integer(0);
		integer = cursor.getInt(0);
		return integer;
	}

	public String cursorToString(Cursor cursor) {
		String outputString = new String();
		outputString = cursor.getString(0);
		return outputString;
	}

}
