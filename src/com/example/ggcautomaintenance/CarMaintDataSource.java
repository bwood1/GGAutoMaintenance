package com.example.ggcautomaintenance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
	public ListItems[] getAllMaintenanceItemsAlphabetical() {
		ListItems[] listItemsArray = new ListItems[22];

		// Select All Query
		String selectQuery = "SELECT a._id, MaintDescription, " +
				"b.MaintDueDate, b.MaintDueMileage " + 
				"FROM " + CarMaintTableHelper.TABLE_MAINT_ITEMS + " a " + 
				"INNER JOIN " + CarMaintTableHelper.TABLE_MAINT_RECORDS + " b " +
				"ON " + "a._id=b._id" + " ORDER BY 2";
		
		Cursor cursor = db.rawQuery(selectQuery, null);

		int i = 0;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ListItems listItem = new ListItems();
				listItem.setMaintId(Integer.parseInt(cursor.getString(0)));
				listItem.setMaintDescription(cursor.getString(1));
				listItem.setDateNextDue(cursor.getString(2));
				listItem.setMileageNextDue(Integer.parseInt(cursor.getString(3)));
				listItemsArray[i] = listItem;
				i++;
			} while (cursor.moveToNext());
		}

		// return Maintenance Items Array
		return listItemsArray;
	}

	// Getting All Maintenance Items sorted by due date
	/**
	 * gets all maint items sorted by due date
	 * @return
	 */
	public ListItems[] getAllMaintenanceItemsDueDate() {
		ListItems[] listItemsArray = new ListItems[22];

		// Select All Query
		String selectQuery = "SELECT a._id, MaintDescription, " +
				"b.MaintDueDate, b.MaintDueMileage " + 
				"FROM " + CarMaintTableHelper.TABLE_MAINT_ITEMS + " a " + 
				"INNER JOIN " + CarMaintTableHelper.TABLE_MAINT_RECORDS + " b " +
				"ON " + "a._id=b._id" + " ORDER BY 3";

		Cursor cursor = db.rawQuery(selectQuery, null);

		int i = 0;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ListItems listItem = new ListItems();
				listItem.setMaintId(Integer.parseInt(cursor.getString(0)));
				listItem.setMaintDescription(cursor.getString(1));
				listItem.setDateNextDue(cursor.getString(2));
				listItem.setMileageNextDue(Integer.parseInt(cursor.getString(3)));
				listItemsArray[i] = listItem;
				i++;
			} while (cursor.moveToNext());
		}

		// return Maintenance Items Array
		return listItemsArray;
	}
	
	/**
	 * returns the mileage interval of the maint id provided
	 * @param maintId
	 * @return - mileageinterval
	 */
	public int getMileageInterval(int maintId) {
		String query = "SELECT mileageinterval FROM maintenanceitems WHERE _id=" + maintId;
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();
		return cursorToInt(cursor);
	}
	
	public void setMileageInterval(int maintId, int miles) {
		
		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MI_MILEAGE_INTERVAL, miles);
		
		db.update(CarMaintTableHelper.TABLE_MAINT_ITEMS, values, CarMaintTableHelper.MI_MAINT_ID + "=" + maintId, null);
	}
	
	public int getTimeInterval(int maintId) {
		String query = "SELECT timeinterval FROM maintenanceitems WHERE _id=" +  maintId;
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();
		return cursorToInt(cursor);
	}
	
	public void setTimeInterval(int maintId, int timeInterval) {
		
		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MI_TIME_INTERVAL, timeInterval);
		
		db.update(CarMaintTableHelper.TABLE_MAINT_ITEMS, values, CarMaintTableHelper.MI_MAINT_ID + "=" + maintId, null);
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
	}

	//Get a single maint record from the database
	/**
	 * Will get a single MaintRecord from the database
	 * @param maintId - ID of the record to be returned
	 * @return - a MaintRecord
	 */
	public MaintRecords getMaintRecord(String maintId) {
		//List<MaintRecords> maintRecordsList = new ArrayList<MaintRecords>();
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
	
	public String getCurrentDate() {
		String query = "SELECT date('now')";
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();
		
		String currentDate;
		currentDate = cursorToString(cursor);
		return currentDate;
	}
	
	/**
	 * updates the maint record row based on the record it
	 * @param maintRecordId
	 * @param maintCompleteDate
	 * @param carName
	 * @param maintId
	 * @param odometer
	 * @param cost
	 * @param maintDueDate
	 * @param maintDueMileage
	 */
	public void updateMaintRecord(int maintRecordId,
			String maintCompleteDate,
			String carName, int maintId,
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
				values, CarMaintTableHelper.MR_MAINT_ID + "=" + maintRecordId, null);
	}

	//gets the next maintenance date due for the specified maintid
	/**
	 * gets the due date of the maint id selected
	 * @param maintId
	 * @return
	 */
	public String getNextMaintDueDate (int maintId) {
		String query = "SELECT MaintDueDate FROM MaintRecord WHERE _id IS " + maintId;
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();

		return cursorToString(cursor);
	}

	/**
	 * gets the due date of the maint id selected
	 * @param maintId
	 * @return
	 */
	public int getMaintDueMileage(int maintId) {
		String query = "SELECT maintduemileage FROM maintrecord WHERE _id IS " +
				maintId;

		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();

		int odometer;
		odometer = cursorToInt(cursor);
		return odometer;
	}
	
	
	/**
	 * Sets the mileage for when maintenance is due
	 * @param maintId
	 */
	public void setMaintDueMileage(int maintId) {
		//calculate the due mileage
		int newMileageDue = getOdometer(maintId) + getMileageInterval(maintId) - getMileage();
		
		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MR_MAINT_DUE_MILEAGE, newMileageDue);
		
		db.update(CarMaintTableHelper.TABLE_MAINT_RECORDS, values, CarMaintTableHelper.MR_MAINT_RECORD_ID + "=" + maintId, null);
	}
	
	//loop through all maintenance items and update their due mileage
	public void maintDueMileageUpdate()
	{
		int i;
		for(i = 1; i < 23; i++)
		{
			setMaintDueMileage(i);
		}
	}
	
	/**
	 * Loops through all of the items in the list and sets the due date
	 */
	public void maintDueDate()
	{		
		int i;
		for(i = 1; i < 23; i++)
		{			
			setMaintDueDate(i);
		}	
	}
	
	/**
	 * Sets the due date for the maintenance item
	 * @param maintId
	 */
	public void setMaintDueDate(int maintId) {
		//calculate the due date		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);		
		Date maintLastDoneDate = new Date();		
		Calendar cal = Calendar.getInstance(); 
		
		try {
			maintLastDoneDate = dateFormat.parse(getMaintCompleteDate(maintId));
		} catch (ParseException e) {

			e.printStackTrace();
		}	//when the maint was done
		cal.setTime(maintLastDoneDate);
		cal.add(Calendar.MONTH, getTimeInterval(maintId));  //add time interval

		String dueDate = "" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + 
				cal.get(Calendar.DAY_OF_MONTH);	
		
		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MR_MAINT_DUE_DATE, dueDate);
		
		db.update(CarMaintTableHelper.TABLE_MAINT_RECORDS, values, CarMaintTableHelper.MR_MAINT_RECORD_ID + "=" + maintId, null);
	}

	//gets the maintenance date completed for the specified maintid
	/**
	 * gett the date completed for the maint id selected
	 * @param maintId
	 * @return
	 */
	public String getMaintCompleteDate (int maintId) {
		String query = "SELECT maintCompleteDate FROM maintrecord WHERE _id IS " + maintId;

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
		String query = "SELECT odometer FROM maintrecord WHERE _id IS " + maintId;

		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();

		int odometer;
		odometer = cursorToInt(cursor);
		return odometer;
	}
	
	// Getting Maintenance Record Count
	/**
	 * gets the count of the maint items
	 * @return
	 */
	public int getMaintRecordCount() {
		String countQuery = "SELECT  * FROM " + CarMaintTableHelper.TABLE_MAINT_RECORDS;
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
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
	public int getFillupMileage() {

		String[] columns = new String[1];
		columns[0] = CarMaintTableHelper.MPG_ODOMETER;

		String[] whereArgs = new String[1];
		whereArgs[0] = "1";

		String query = "SELECT odometer FROM mpg WHERE _id IS 1";

		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();

		int mileage = cursorToInt(cursor);
		return mileage;
	}

	/**
	 * sets the old odomter (ie current fill up or row 1 on the table
	 * @param oldOdometer
	 */
	public void setFillupMileage(int oldOdometer) {
		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MPG_ODOMETER, oldOdometer);

		String[] whereArgs = new String[1];
		whereArgs[0] = "1";

		db.update(CarMaintTableHelper.TABLE_MPG, 
				values, CarMaintTableHelper.MPG_FILL_NUMBER	+ "=1" ,null);
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

		db.update(CarMaintTableHelper.TABLE_MPG, values, 
				CarMaintTableHelper.MPG_FILL_NUMBER + "=2", null);
	}
	
	/**
	 * Stores the mileage into the database (row 3)
	 * @param odometer
	 */
	public void setMileage(int odometer) {
		ContentValues values = new ContentValues();
		values.put(CarMaintTableHelper.MPG_ODOMETER, odometer);
		
		db.update(CarMaintTableHelper.TABLE_MPG, values, CarMaintTableHelper.MPG_FILL_NUMBER + "=3", null);
	}
	
	/**
	 * gets the current mileage from the database (row 3 of mpg table)
	 * @return
	 */
	public int getMileage() {
		String query = "SELECT odometer FROM mpg WHERE _id IS 3";
		
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();
		
		return cursorToInt(cursor);
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

		String query = "SELECT odometer FROM mpg WHERE _id IS 2";

		Cursor test = db.rawQuery(query, null);
		test.moveToFirst();
		
		return cursorToInt(test);
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
