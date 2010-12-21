package com.islamsharabash.cumtd;

import java.io.*;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{
	
	//The Android's default system path of your application database. 
	private static String DB_NAME = "cumtdDB.db";
	private static final String DATABASE_TABLE ="stopTable";
    private static String DB_PATH = "/data/data/com.islamsharabash.cumtd/databases/";
    
	private static final int VERSION = 2;
	private SQLiteDatabase myDataBase; 
	private final Context myContext;
	 
	//Database fields
	public static final String KEY_ID = "_id";
	public static final String STOP_ID = "_stop";
	public static final String NAME = "_name";
	public static final String FAVORITES = "_favorites";
	public static final String LATITUDE = "_latitude";
	public static final String LONGITUDE = "_longitude";

	
    
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		// check if we need to upgrade
    		openDataBase();
    		if(myDataBase.getVersion() != VERSION)
    			onUpgrade(myDataBase, myDataBase.getVersion(), VERSION);

    	}else{
 
    		// By calling this method and empty database will be created into the default system path
            // of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
        	try {
        		
        		// copy data
    			copyDataBase();
    			
    			// set database version
    			openDataBase();
    			myDataBase.setVersion(VERSION);
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
    
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
    
	@Override
	public void onCreate(SQLiteDatabase db) {}
 

	/**
	 * onUpgrade saves all the favorites, and install the new database
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Vector<Integer> favStops = getOldFavoriteStops(db);
		
		// delete the old db and make a new one
		db.close();
		File dbFile = new File(DB_PATH + DB_NAME);
		dbFile.delete();
		
		try {
			createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// finally reset favorites
		setOldFavoriteStops(favStops);
	}
	
	/**
	 * @param db
	 * @return Vector of Integers with stopIDs of favorites
	 */
	private Vector<Integer> getOldFavoriteStops(SQLiteDatabase db) {
		Cursor oldCursor = db.query(DATABASE_TABLE,
				new String[] {KEY_ID, STOP_ID},
				"_book = 1",
				null, null, null, "_loc ASC");

		Vector<Integer> favStops = new Vector<Integer>();

		// get all stops that 
		if(!oldCursor.moveToFirst()){
			while(!oldCursor.isAfterLast()) {
				favStops.add(oldCursor.getInt(1));
			}
		}
		
		return favStops;
	}
	
	/**
	 * setOldFavoriteStops sets each stopID as a favorite in favStops
	 * @param favStops
	 */
	private void setOldFavoriteStops(Vector<Integer> favStops) {
		openDataBase();

		for (int i = 0; i < favStops.size(); i++) {
			setFavorite(new Stop(favStops.get(i)));	
		}
		
		close();
	}
 
	/**
	 * Sets the specified stop as a favorite
	 * @param _stop
	 * @return true if setting the favorite succeeded, else false
	 * @throws MalformedStop
	 */
	public boolean setFavorite(Stop _stop) {
		ContentValues newValues = new ContentValues();
		newValues.put(FAVORITES, 1);		
		if ((myDataBase.update(DATABASE_TABLE,
								newValues,
								STOP_ID + " = ?",
								new String[] {Integer.toString(_stop.getStopID())})) == 1)
			return true;
		
		else return false;			
	}
	
	/**
	 * @return cursor with every row in database
	 */
	public Cursor getAllStops(){
		return myDataBase.query(DATABASE_TABLE,
								new String[] {KEY_ID, NAME, STOP_ID, FAVORITES, LATITUDE, LONGITUDE},
								null, null, null, null, NAME + " ASC");
	}
	
	/**
	 * @return all rows that are favorites
	 */
	public Cursor getFavorites(){
		return myDataBase.query(DATABASE_TABLE,
								new String[] {KEY_ID, NAME, STOP_ID, FAVORITES, LATITUDE, LONGITUDE},
								FAVORITES + " = 1", null, null, null, NAME + " ASC");
	}
	
	/**
	 * sets the stop specified by _rowIndex as not a favorite
	 * @param _rowIndex
	 * @return true if succeeded, else false
	 */
	public boolean removeFavorite(Stop _stop) {
		ContentValues newValues = new ContentValues();
		newValues.put(FAVORITES, 0);		
		if ((myDataBase.update(DATABASE_TABLE,
								newValues,
								STOP_ID + " = ?",
								new String[] {Integer.toString(_stop.getStopID())})) == 1)
			return true;
		
		else return false;			
	}
	
	/**
	 * Used to filter by location name
	 * @param constraint
	 * @return Cursor with updated rows
	 */
	public Cursor filter(String constraint){
		return myDataBase.query(DATABASE_TABLE,
								new String[] {KEY_ID, NAME, STOP_ID, FAVORITES, LATITUDE, LONGITUDE},
								NAME + " LIKE '%" + constraint + "%'",
								null, null, null, NAME + " ASC");
	}
	
	/**
	 * 
	 * @param _rowIndex
	 * @return Stop object specified by _rowIndex
	 */
	public Stop getStop(long _rowIndex){
		//query the database for the stop_id and location, put it in a stop object
		Cursor mCursor = myDataBase.query(DATABASE_TABLE,
											new String[] {KEY_ID, NAME, STOP_ID, FAVORITES, LATITUDE, LONGITUDE},
											KEY_ID + "= ?",
											new String[] {Long.toString(_rowIndex)},
											null, null, null );
		mCursor.moveToFirst();
		
		/** Note to self, if you change the db, you have to change how it gets values! (It is the order you query) **/
		return new Stop(mCursor.getInt(0),
						mCursor.getString(1),
						mCursor.getDouble(2),
						mCursor.getDouble(3));
	}
 
}