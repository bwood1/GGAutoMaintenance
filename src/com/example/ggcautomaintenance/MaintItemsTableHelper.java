package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MaintItemsTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMMENTS = "maintenanceitems";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";

	private static final int DATABASE_VERSION = 1;
	
	//Database creation statement
	private static final String CREATE_MAINTITEMS_TABLE = "CREATE TABLE " + TABLE_COMMENTS +
																"(maintid INTEGER PRIMARY KEY AUTOINCREMENT, " +
																"maintenancedescription TEXT(20), " +
																"mileageinterval INTEGER, " +
																"timeinterval INTEGER);";

	public MaintItemsTableHelper(Context context) {
		super(context, CREATE_MAINTITEMS_TABLE, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MAINTITEMS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MaintItemsTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		onCreate(db);
	}

}