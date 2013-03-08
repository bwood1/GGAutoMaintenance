package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMMENTS = "comments";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";

	private static final int DATABASE_VERSION = 1;
	
	//Database creation statement
	private static final String CREATE_CAR_TABLE = "create table car(CarName TEXT(20) not null, " +
																			"Make TEXT(15), " +
																			"Model TEXT(20), " +
																			"Odometer INTEGER, " +
																			"PRIMARY KEY(CarName));";

	public CarTableHelper(Context context) {
		super(context, CREATE_CAR_TABLE, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
