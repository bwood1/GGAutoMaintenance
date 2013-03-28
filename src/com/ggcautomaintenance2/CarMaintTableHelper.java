package com.ggcautomaintenance2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class CarMaintTableHelper extends SQLiteOpenHelper{

	// Static Variables for Maintenance Items Table
	public static final String TABLE_MAINT_ITEMS = "MaintenanceItems";
	public static final String MI_MAINT_ID = "_id";
	public static final String MI_MAINT_DESCRIPTION = "MaintDescription";
	public static final String MI_MILEAGE_INTERVAL = "MileageInterval";
	public static final String MI_TIME_INTERVAL = "TimeInterval";

	// Static Variable for Maintenance Records Table
	public static final String TABLE_MAINT_RECORDS = "MaintRecord";
	public static final String MR_MAINT_RECORD_ID = "_id";
	public static final String MR_MAINT_COMPLETE_DATE = "MaintCompleteDate";
	public static final String MR_CAR_NAME = "CarName";
	public static final String MR_MAINT_ID = "MaintID";
	public static final String MR_ODOMETER = "Odometer";
	public static final String MR_COST = "Cost";
	public static final String MR_MAINT_DUE_DATE = "MaintDueDate";
	public static final String MR_MAINT_DUE_MILEAGE = "MaintDueMileage";

	// Static Variables for MPG Table
	public static final String TABLE_MPG = "mpg";
	public static final String MPG_FILL_NUMBER = "_id";
	public static final String MPG_CAR_NAME = "CarName";
	public static final String MPG_ODOMETER = "Odometer";
	public static final String MPG_GALLONS = "Gallons";
	public static final String MPG_COST_PER_GALLON = "CostPerGallon";

	//The Android's default system path of your application database.
	@SuppressLint("SdCardPath")
	private static String DB_PATH = "/data/data/com.ggcautomaintenance2/databases/";

	private static String DB_NAME = "carDatabase.db";

	private SQLiteDatabase myDataBase; 

	private final Context myContext;

	/**
	 * Constructor
	 * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	 * @param context
	 */
	public CarMaintTableHelper(Context context) {

		super(context, DB_NAME, null, 5);
		this.myContext = context;
	}	

	/**
	 * Creates a empty database on the system and rewrites it with your own database.
	 * */
	public void createDataBase() throws IOException{

		boolean dbExist = checkDataBase();

		if(dbExist){
			//do nothing - database already exist
		}else{

			//By calling this method and empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();

			try {
				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase(){

		SQLiteDatabase checkDB = null;

		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

		}catch(SQLiteException e){

			//database does't exist yet.
		}

		if(checkDB != null){

			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException{

		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
	
	/**
	 * Opens path to database
	 * @throws SQLException
	 */
	public void openDataBase() throws SQLException{

		//Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}
	
	/**
	 * Closes the database
	 */
	@Override
	public synchronized void close() {

		if(myDataBase != null)
			myDataBase.close();

		super.close();
	}
	
	/**
	 * OnCreate method
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

	}
	
	/**
	 * onUpgrade method
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// Add your public helper methods to access and get content from the database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
	// to you to create adapters for your views.

}