package com.example.ggcautomaintenance;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MaintItemsTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_MAINT_ITEMS = "MaintenanceRecord";
	public static final String COLUMN_MAINT_ID = "MaintID";
	public static final String COLUMN_MAINT_DESCRIPTION = "MaintDescription";
	public static final String COLUMN_MILEAGE_INTERVAL = "MileageInterval";
	public static final String COLUMN_TIME_INTERVAL = "TimeInterval"; 

	private static final String DATABASE_NAME = "carDatabase.db";
	private static final int DATABASE_VERSION = 1;

	//Database creation statement
	private static final String CREATE_MAINT_ITEMS_TABLE = "CREATE TABLE  " + 
			TABLE_MAINT_ITEMS + "(" + 
			COLUMN_MAINT_ID + "INTEGER PRIMARY KEY " +
			"AUTOINCREMENT, " + 
			COLUMN_MAINT_DESCRIPTION + "TEXT(20), " + 
			COLUMN_MILEAGE_INTERVAL + "INTEGER, " + 
			COLUMN_TIME_INTERVAL + "INTEGER);";

	public MaintItemsTableHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MAINT_ITEMS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(MaintItemsTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destory all old data");
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_MAINT_ITEMS_TABLE);
		onCreate(db);
	}

	// Adding new Maintenance Items
	public void addMaintenanceItem(MaintItems maintItem) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_MAINT_ID, maintItem.getMaintId()); // Maintenance Item ID
		values.put(COLUMN_MAINT_DESCRIPTION, maintItem.getMaintDescription()); // Maintenance Item Description
		values.put(COLUMN_MILEAGE_INTERVAL, maintItem.getMileageInterval()); // Maintenance Item Mileage Interval
		values.put(COLUMN_TIME_INTERVAL, maintItem.getTimeInterval()); // Maintenance Item Time Interval

		// Inserting Row
		db.insert(TABLE_MAINT_ITEMS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single maintenance item
	public MaintItems getMaintenanceItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MAINT_ITEMS, new String[] { COLUMN_MAINT_ID,
				COLUMN_MAINT_DESCRIPTION, COLUMN_MILEAGE_INTERVAL, COLUMN_TIME_INTERVAL }, COLUMN_MAINT_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		MaintItems maintItem = new MaintItems(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));
		// return contact
		return maintItem;
	}

	// Getting All Maintenance items
	public List<MaintItems> getAllMaintenanceItems() {
		List<MaintItems> maintItemsList = new ArrayList<MaintItems>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_MAINT_ITEMS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				MaintItems maintItem = new MaintItems();
				maintItem.setMaintId(Integer.parseInt(cursor.getString(0)));
				maintItem.setMaintDescription(cursor.getString(1));
				maintItem.setMileageInterval(Integer.parseInt(cursor.getString(2)));
				maintItem.setTimeInterval(Integer.parseInt(cursor.getString(3)));
				// Adding contact to list
				maintItemsList.add(maintItem);
			} while (cursor.moveToNext());
		}

		// return contact list
		return maintItemsList;
	}

	// Getting Maintenance Items Count
	public int getMaintenanceItemsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_MAINT_ITEMS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
	
	// Updating single contact
	public int updateContact(MaintItems maintItem) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(COLUMN_MAINT_ID, maintItem.getMaintId());
	    values.put(COLUMN_MAINT_DESCRIPTION, maintItem.getMaintDescription());
	    values.put(COLUMN_MILEAGE_INTERVAL, maintItem.getMileageInterval());
	    values.put(COLUMN_TIME_INTERVAL, maintItem.getTimeInterval());
	 
	    // updating row
	    return db.update(TABLE_MAINT_ITEMS, values, COLUMN_MAINT_ID + " = ?",
	            new String[] { String.valueOf(maintItem.getMaintId())});
	}
	
	// Deleting single contact
	public void deleteContact(MaintItems maintItem) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_MAINT_ITEMS, COLUMN_MAINT_ID + " = ?",
	            new String[] { String.valueOf(maintItem.getMaintId()) });
	    db.close();
	}
}
