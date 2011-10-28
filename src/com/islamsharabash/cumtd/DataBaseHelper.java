package com.islamsharabash.cumtd;

import java.io.*;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

// code to copy db from assets was found on net, forgot from where
public class DatabaseHelper extends SQLiteOpenHelper{
	
	//The Android's default system path of your application database. 
	private static String DB_NAME = "cumtdDB.db";
	private static String DB_PATH = "/data/data/com.islamsharabash.cumtd/databases/";
    
	private static final int VERSION = 5;
	private SQLiteDatabase database; 
	private final Context context;
	 
    /**
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context) {
    	super(context, DB_NAME, null, 1);
        this.context = context;
    }	
    
	@Override
	public void onCreate(SQLiteDatabase db) {
	}
 
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDatabase() throws IOException{
    	
    	boolean dbExist = checkDatabase();
 
    	if (dbExist) {
    		// check if we need to upgrade
    		openDatabase();
    		int cVersion = database.getVersion();
    		close();
    		if (cVersion != VERSION) {
    			onUpgrade(database, cVersion, VERSION);
    		}
    		
    	} else {
 
    		// By calling this method and empty database will be created into the default system path
            // of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
        	try {
        		// copy data
    			copyDatabase();
    			
    			// set database version
    			openDatabase();
    			database.setVersion(VERSION);
    			close();
    			
        	} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDatabase(){

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
    private void copyDatabase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = context.getAssets().open(DB_NAME);
 
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
 
    public void openDatabase() throws SQLException{
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
 
    @Override
	public synchronized void close() {
    	    if(database != null)
    		    database.close();
 
    	    super.close();
	}
    
	/**
	 * onUpgrade upgrades from an old db to a new db
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// starting fresh, delete any database that's not current
		// sorry about the favorites :(
		if (oldVersion != DatabaseHelper.VERSION) {
			deleteRecreate(db);
		}
	}
	
	public SQLiteDatabase getDatabase() {
		return database;
	}
	
	/**
	 * deletes and recreates the database, use extreme caution with this
	 * @param db
	 */
	private void deleteRecreate(SQLiteDatabase db) {
		if (db.isOpen())
			close();
		
		File dbFile = new File(DB_PATH + DB_NAME);
		dbFile.delete();
		
		try {
			createDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//TODO(ibash) remove the code below, it's going into DatabaseAPI	
	
	
	/**
	 * Sets the specified stop as a favorite
	 * @param _stop
	 * @return true if setting the favorite succeeded, else false
	 * @throws MalformedStop
	public boolean setFavorite(Stop _stop) {
		ContentValues newValues = new ContentValues();
		newValues.put(FAVORITE, 1);		
		if ((database.update(STOP_TABLE,
								newValues,
								STOP_ID + " = ?",
								new String[] {Integer.toString(_stop.getStopID())})) == 1)
			return true;
		
		else return false;			
	}

	/**
	 * returns a cursor with stops with the bounding box of the distance
	 * set out by the mile multiplier d
	 * 
	 * @param lat
	 * @param lng
	 * @param d
	 * @return
	public Cursor nearStops(int lat, int lng, double d) {
		int latUpper = (int) (lat + (14460 * d));
		int latLower  = (int) (lat - (14460 * d));
		int longUpper = (int) (lng + (18926 * d));
		int longLower = (int) (lng - (18926 * d));
		
		return boundStops(latUpper, latLower, longUpper, longLower);
	}
	
	/**
	 * returns cursor of stops within the bounds specified
	 * @param longUpper
	 * @param longLower
	 * @param latUpper
	 * @param latLower
	 * @return
	public Cursor boundStops(int latUpper, int latLower, int longUpper, int longLower) {
		
		String query = "SELECT " + STOP_ID + ", " + NAME + ", " + LATITUDE + ", " +
						LONGITUDE + ", " + FAVORITE + ", " + KEY_ID + " FROM " + STOP_TABLE + 
						" WHERE " + LATITUDE + " BETWEEN " + Integer.toString(latLower) +
						" AND " + Integer.toString(latUpper) +
						" INTERSECT " +
						"SELECT " + STOP_ID + ", " + NAME + ", " + LATITUDE + ", " +
						LONGITUDE + ", " + FAVORITE + ", " + KEY_ID + " FROM " + STOP_TABLE + 
						" WHERE " + LONGITUDE + " BETWEEN " + Integer.toString(longLower) +
						" AND " + Integer.toString(longUpper);
		
		return database.rawQuery(query, new String[] {});
	}
	
	/**
	 * sets the stop specified by _rowIndex as not a favorite
	 * @param _rowIndex
	 * @return true if succeeded, else false
	public boolean removeFavorite(Stop _stop) {
		ContentValues newValues = new ContentValues();
		newValues.put(FAVORITE, 0);		
		if ((database.update(STOP_TABLE,
								newValues,
								STOP_ID + " = ?",
								new String[] {Integer.toString(_stop.getStopID())})) == 1)
			return true;
		
		else return false;			
	}


**/	
}