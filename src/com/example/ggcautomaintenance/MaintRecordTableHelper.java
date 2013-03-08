package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaintRecordTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMMENTS = "comments";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";

	private static final int DATABASE_VERSION = 1;
	
	//Database creation statement
	private static final String CREATE_MAINTRECORD_TABLE = "create table MaintenanceRecord" +
															"(MaintRecordID INTEGER PRIMARY KEY AUTOINCREMENT, " +
															"MaintCompleteDate DATE, " +
															"CarName TEXT(20), " +
															"MaintID INTEGER, " +
															"Odometer INTEGER, " +
															"Cost, CURRENCY);";

	public MaintRecordTableHelper(Context context) {
		super(context, CREATE_MAINTRECORD_TABLE, null, DATABASE_VERSION);
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