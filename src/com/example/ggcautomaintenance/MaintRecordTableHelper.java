package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MaintRecordTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMMENTS = "maintenancerecord";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";

	private static final int DATABASE_VERSION = 1;
	
	//Database creation statement
	private static final String CREATE_MAINTRECORD_TABLE = "create table " + TABLE_COMMENTS + 
															"(maintrecordid INTEGER PRIMARY KEY AUTOINCREMENT, " +
															"maintcompletedate DATE, " +
															"carname TEXT(20), " +
															"maintid INTEGER, " +
															"odometer INTEGER, " +
															"cost, CURRENCY, " +
															"FOREIGN KEY(carname) REFRENCES car(carname)" +
															"FOREIGN KEY(maintid) REFRENCES maintenanceitems(maintid);";

	public MaintRecordTableHelper(Context context) {
		super(context, CREATE_MAINTRECORD_TABLE, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MAINTRECORD_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MaintRecordTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		onCreate(db);
	}

}