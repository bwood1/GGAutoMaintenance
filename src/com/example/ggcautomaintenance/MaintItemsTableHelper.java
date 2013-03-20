package com.example.ggcautomaintenance;

//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.ContentValues;
import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MaintItemsTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_MAINT_ITEMS = "MaintenanceItems";
	public static final String COLUMN_MAINT_ID = "MaintID";
	public static final String COLUMN_MAINT_DESCRIPTION = "MaintDescription";
	public static final String COLUMN_MILEAGE_INTERVAL = "MileageInterval";
	public static final String COLUMN_TIME_INTERVAL = "TimeInterval"; 

	private static final String DATABASE_NAME = "carDatabase.db";
	private static final int DATABASE_VERSION = 2;

//	private SQLiteDatabase db;
//	private MaintItemsTableHelper maintItemsDbHelper;

	//Database creation statement
	private static final String CREATE_MAINT_ITEMS_TABLE = "CREATE TABLE  " + 
			TABLE_MAINT_ITEMS + "(" + 
			COLUMN_MAINT_ID + " INTEGER PRIMARY KEY, " +
			COLUMN_MAINT_DESCRIPTION + " TEXT(20), " + 
			COLUMN_MILEAGE_INTERVAL + " INTEGER, " + 
			COLUMN_TIME_INTERVAL + " INTEGER);";
	
	private static final String CREATE_CAR_TABLE = "CREATE TABLE " + CarTableHelper.TABLE_CAR +  "(" +
			CarTableHelper.COLUMN_CAR_NAME + " TEXT(20) not null, " +
			CarTableHelper.COLUMN_MAKE + " TEXT(15), " +
			CarTableHelper.COLUMN_MODEL + " TEXT(20), " +
			CarTableHelper.COLUMN_ODOMETER + " INTEGER, " +
			"PRIMARY KEY(CarName));";
	
	private static final String CREATE_MPG_TABLE = "CREATE TABLE " + MPGTableHelper.TABLE_COMMENTS + "(" + 
			MPGTableHelper.COLUMN_FILL_NUMBER + " INTEGER PRIMARY KEY, " +
			MPGTableHelper.COLUMN_CAR_NAME + " TEXT(20), " +
			MPGTableHelper.COLUMN_ODOMETER +  " INTEGER, " +
			MPGTableHelper.COLUMN_GALLONS + " INTEGER, " +
			MPGTableHelper.COLUMN_COST_PER_GALLON + " CURRENCY);";
	
	private static final String CREATE_MAINTRECORD_TABLE = "CREATE TABLE " + MaintRecordTableHelper.TABLE_MAINT_RECORDS_TABLE + "(" +
			MaintRecordTableHelper.COLUMN_MAINT_RECORD_ID + " INTEGER PRIMARY KEY, " +
			MaintRecordTableHelper.COLUMN_MAINT_COMPLETE_DATE + " TEXT, " +
			MaintRecordTableHelper.COLUMN_CAR_NAME + " TEXT(20), " +
			MaintRecordTableHelper.COLUMN_MAINT_ID + " INTEGER, " +
			MaintRecordTableHelper.COLUMN_ODOMETER + " INTEGER, " +
			MaintRecordTableHelper.COLUMN_COST + ", CURRENCY, " +
			MaintRecordTableHelper.COLUMN_MAINT_DUE_DATE + ", TEXT, " + 
			MaintRecordTableHelper.COLUMN_MAINT_DUE_MILEAGE + ", INTEGER);";
	
	//CREATE TABLE MaintItems(maintId INTEGER PRIMARY KEY, maintdescription TEXT(20), mileageinterval INTEGER, timeinterval INTEGER);

	public MaintItemsTableHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MAINT_ITEMS_TABLE);
		db.execSQL(CREATE_CAR_TABLE);
		db.execSQL(CREATE_MPG_TABLE);
		db.execSQL(CREATE_MAINTRECORD_TABLE);
		System.out.println("database execution statement run");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(MaintItemsTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destory all old data");
		db.execSQL("DROP TABLE IF EXISTS MaintenanceRecord");
		onCreate(db);
	}

//	//Open database
//	public void open() throws SQLException {
//		db = maintItemsDbHelper.getWritableDatabase();
//	}
//
//	//Close database
//	public void close() {
//		maintItemsDbHelper.close();
//	}

//	// Adding new Maintenance Items
//	public void addMaintenanceItem(int maintId, String maintDesc, int mileageInterval, int timeInterval) {
////		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(COLUMN_MAINT_ID, maintId); // Maintenance Item ID
//		values.put(COLUMN_MAINT_DESCRIPTION, maintDesc); // Maintenance Item Description
//		values.put(COLUMN_MILEAGE_INTERVAL, mileageInterval); // Maintenance Item Mileage Interval
//		values.put(COLUMN_TIME_INTERVAL, timeInterval); // Maintenance Item Time Interval
//
//		// Inserting Row
//		db.insert(TABLE_MAINT_ITEMS, null, values);
////		db.close(); // Closing database connection
//	}
//
//	// Getting single maintenance item
//	public MaintItems getMaintenanceItem(int id) {
////		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(TABLE_MAINT_ITEMS, new String[] { COLUMN_MAINT_ID,
//				COLUMN_MAINT_DESCRIPTION, COLUMN_MILEAGE_INTERVAL, COLUMN_TIME_INTERVAL }, COLUMN_MAINT_ID + "=?",
//				new String[] { String.valueOf(id) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//
//		MaintItems maintItem = new MaintItems(Integer.parseInt(cursor.getString(0)),
//				cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));
//		// return contact
//		return maintItem;
//	}
//
//	// Getting All Maintenance items
//	public MaintItems[] getAllMaintenanceItems() {
//		MaintItems[] maintItemsArray = new MaintItems[22/*need to make into a variable if scope changes*/];
//		
////		List<MaintItems> maintItemsList = new ArrayList<MaintItems>();
//		// Select All Query
//		String selectQuery = "SELECT  * FROM " + TABLE_MAINT_ITEMS;
//
////		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		int i = 0;
//	
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				MaintItems maintItem = new MaintItems();
//				maintItem.setMaintId(Integer.parseInt(cursor.getString(0)));
//				maintItem.setMaintDescription(cursor.getString(1));
//				maintItem.setMileageInterval(Integer.parseInt(cursor.getString(2)));
//				maintItem.setTimeInterval(Integer.parseInt(cursor.getString(3)));
//				// Adding contact to list
////				maintItemsList.add(maintItem);
//				maintItemsArray[i] = maintItem;
//				i++;
//			} while (cursor.moveToNext());
//		}
//
//		// return contact list
//		return maintItemsArray;
//	}
//
//	// Getting Maintenance Items Count
//	public int getMaintenanceItemsCount() {
//		String countQuery = "SELECT  * FROM " + TABLE_MAINT_ITEMS;
////		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cursor = db.rawQuery(countQuery, null);
//		cursor.close();
//
//		// return count
//		return cursor.getCount();
//	}
//
//	// Updating single contact
//	public int updateContact(MaintItems maintItem) {
////		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(COLUMN_MAINT_ID, maintItem.getMaintId());
//		values.put(COLUMN_MAINT_DESCRIPTION, maintItem.getMaintDescription());
//		values.put(COLUMN_MILEAGE_INTERVAL, maintItem.getMileageInterval());
//		values.put(COLUMN_TIME_INTERVAL, maintItem.getTimeInterval());
//
//		// updating row
//		return db.update(TABLE_MAINT_ITEMS, values, COLUMN_MAINT_ID + " = ?",
//				new String[] { String.valueOf(maintItem.getMaintId())});
//	}
//
//	// Deleting single contact
//	public void deleteContact(MaintItems maintItem) {
////		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_MAINT_ITEMS, COLUMN_MAINT_ID + " = ?",
//				new String[] { String.valueOf(maintItem.getMaintId()) });
////		db.close();
//	}
}
