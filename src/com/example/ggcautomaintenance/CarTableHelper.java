package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CarTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMMENTS = "car";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";

	private static final int DATABASE_VERSION = 1;
	
	//Database creation statement
	private static final String CREATE_CAR_TABLE = "create table " + TABLE_COMMENTS +  
																	"(CarName TEXT(20) not null, " +
																	"Make TEXT(15), " +
																	"Model TEXT(20), " +
																	"Odometer INTEGER, " +
																	"PRIMARY KEY(CarName));";

	public CarTableHelper(Context context) {
		super(context, CREATE_CAR_TABLE, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CAR_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(CarTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		onCreate(db);
	}

}
