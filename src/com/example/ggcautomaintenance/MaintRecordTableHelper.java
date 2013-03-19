package com.example.ggcautomaintenance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MaintRecordTableHelper extends SQLiteOpenHelper {

	public static final String TABLE_MAINT_RECORDS_TABLE = "maintenancerecord";
	public static final String COLUMN_MAINT_RECORD_ID = "MaintRecordId";
	public static final String COLUMN_MAINT_COMPLETE_DATE = "MaintCompleteDate";
	public static final String COLUMN_CAR_NAME = "CarName";
	public static final String COLUMN_MAINT_ID = "MaintId";
	public static final String COLUMN_ODOMETER = "Odometer";
	public static final String COLUMN_COST = "Cost";
	public static final String COLUMN_MAINT_DUE_DATE = "MaintDueDate";
	public static final String COLUMN_MAINT_DUE_MILEAGE = "MaintDueMileage";

	private static final String DATABASE_NAME = "carDatabase.db";
	private static final int DATABASE_VERSION = 1;
	
	//Database creation statement
	private static final String CREATE_MAINTRECORD_TABLE = "create table " + 
											TABLE_MAINT_RECORDS_TABLE + "(" +
											COLUMN_MAINT_RECORD_ID + 
												" INTEGER PRIMARY KEY " +"AUTOINCREMENT, " +
											COLUMN_MAINT_COMPLETE_DATE + " DATE, " +
											COLUMN_CAR_NAME + " TEXT(20), " +
											COLUMN_MAINT_ID + " INTEGER, " +
											COLUMN_ODOMETER + " INTEGER, " +
											COLUMN_COST + ", CURRENCY, " +
											COLUMN_MAINT_DUE_DATE + ", DATE, " + 
											COLUMN_MAINT_DUE_MILEAGE + ", INTEGER, " +
											"FOREIGN KEY(" + COLUMN_CAR_NAME + ") " +
												"REFRENCES " + CarTableHelper.TABLE_CAR + 
													"(" + CarTableHelper.COLUMN_CAR_NAME + 
														")" +
											"FOREIGN KEY(" + COLUMN_MAINT_ID + ") " +
												"REFRENCES " + MaintItemsTableHelper.TABLE_MAINT_ITEMS + 
													"(" + MaintItemsTableHelper.COLUMN_MAINT_ID +");";

	public MaintRecordTableHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAINT_RECORDS_TABLE);
		onCreate(db);
	}
}