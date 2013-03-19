package com.example.ggcautomaintenance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MaintItemsTableDataSource {

	//Database fields
	private SQLiteDatabase db;
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
	public SQLiteDatabase open() throws SQLException {
		db = maintItemsDbHelper.getWritableDatabase();
		return db;
	}

	//Close database
	public void close() {
		maintItemsDbHelper.close();
	}
	
	public void dropMaintItemsTable() {
		db.delete(MaintItemsTableHelper.TABLE_MAINT_ITEMS, null, null);
	}

	//add new maint items to table
	public MaintItems addNewMaintItem(int maintId, String maintDescription, 
			int mileageInterval, int timeInterval) {
		//TODO stuff to add new MaintItem to table


		return new MaintItems();
	}

	//TODO methods to get mileage and time intervals for maint items.

	// Adding new Maintenance Items
	public void addMaintenanceItem(int maintId, String maintDesc, int mileageInterval, int timeInterval) {
		//			SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MaintItemsTableHelper.COLUMN_MAINT_ID, maintId); // Maintenance Item ID
		values.put(MaintItemsTableHelper.COLUMN_MAINT_DESCRIPTION, maintDesc); // Maintenance Item Description
		values.put(MaintItemsTableHelper.COLUMN_MILEAGE_INTERVAL, mileageInterval); // Maintenance Item Mileage Interval
		values.put(MaintItemsTableHelper.COLUMN_TIME_INTERVAL, timeInterval); // Maintenance Item Time Interval

		// Inserting Row
		db.insert(MaintItemsTableHelper.TABLE_MAINT_ITEMS, null, values);
		//			db.close(); // Closing database connection
	}

	// Getting single maintenance item
	public MaintItems getMaintenanceItem(int id) {
		//			SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(MaintItemsTableHelper.TABLE_MAINT_ITEMS, new String[] { 
				MaintItemsTableHelper.COLUMN_MAINT_ID,
				MaintItemsTableHelper.COLUMN_MAINT_DESCRIPTION, 
				MaintItemsTableHelper.COLUMN_MILEAGE_INTERVAL, 
				MaintItemsTableHelper.COLUMN_TIME_INTERVAL }, 
				MaintItemsTableHelper.COLUMN_MAINT_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		MaintItems maintItem = new MaintItems(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));
		// return contact
		return maintItem;
	}

	// Getting All Maintenance items
	public MaintItems[] getAllMaintenanceItemsAlphabetical() {
		MaintItems[] maintItemsArray = new MaintItems[22/*need to make into a variable if scope changes*/];

		//			List<MaintItems> maintItemsList = new ArrayList<MaintItems>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + MaintItemsTableHelper.TABLE_MAINT_ITEMS + 
								" ORDER BY " + MaintItemsTableHelper.COLUMN_MAINT_DESCRIPTION;

		//			SQLiteDatabase db = this.getWritableDatabase();
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
				// Adding contact to list
				//					maintItemsList.add(maintItem);
				maintItemsArray[i] = maintItem;
				i++;
			} while (cursor.moveToNext());
		}

		// return contact list
		return maintItemsArray;
	}

	// Getting Maintenance Items Count
	public int getMaintenanceItemsCount() {
		String countQuery = "SELECT  * FROM " + MaintItemsTableHelper.TABLE_MAINT_ITEMS;
		//			SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	// Updating single contact
	public int updateContact(MaintItems maintItem) {
		//			SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MaintItemsTableHelper.COLUMN_MAINT_ID, maintItem.getMaintId());
		values.put(MaintItemsTableHelper.COLUMN_MAINT_DESCRIPTION, maintItem.getMaintDescription());
		values.put(MaintItemsTableHelper.COLUMN_MILEAGE_INTERVAL, maintItem.getMileageInterval());
		values.put(MaintItemsTableHelper.COLUMN_TIME_INTERVAL, maintItem.getTimeInterval());

		// updating row
		return db.update(MaintItemsTableHelper.TABLE_MAINT_ITEMS, values, 
				MaintItemsTableHelper.COLUMN_MAINT_ID + " = ?",
				new String[] { String.valueOf(maintItem.getMaintId())});
	}

	// Deleting single contact
	public void deleteContact(MaintItems maintItem) {
		//			SQLiteDatabase db = this.getWritableDatabase();
		db.delete(MaintItemsTableHelper.TABLE_MAINT_ITEMS, 
				MaintItemsTableHelper.COLUMN_MAINT_ID + " = ?",
				new String[] { String.valueOf(maintItem.getMaintId()) });
		//			db.close();
	}

}
