package com.islamsharabash.cumtd;

import java.io.*;

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
	//private static final int DATABASE_VERSION = 2;
	private SQLiteDatabase myDataBase; 
	private final Context myContext;
	 
	//Database fields
	public static final String KEY_ID ="_id";
	public static final String STOP_ID="_stop";
	public static final String LOCATION="_loc";
	public static final String BOOKMARK="_book";
	
	

 
    
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
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
	public boolean saveBookmark(Stop _stop) throws MalformedStop{
		ContentValues newValues = new ContentValues();
		newValues.put(BOOKMARK, 1);		
		if( (myDataBase.update(DATABASE_TABLE, newValues, STOP_ID + " = ?", new String[] {Integer.toString(_stop.getStopID())})) == 1)
			return true;
		//fuck yeah... screw good code, throw a MalformedStop if it's not there!
		else throw new MalformedStop();			
	}
	
	public Cursor getAllEntries(){
		return myDataBase.query(DATABASE_TABLE, new String[] {KEY_ID, LOCATION, STOP_ID}, null, null, null, null, LOCATION + " ASC");
	}
	
	public Cursor getBookmarks(){
		return myDataBase.query(DATABASE_TABLE, new String[] {KEY_ID, LOCATION, STOP_ID}, BOOKMARK + " = 1", null, null, null, LOCATION + " ASC");
	}
	
	public void removeBookmark(long _rowIndex){
		ContentValues newValues = new ContentValues();
		newValues.put(BOOKMARK, 0);		
		myDataBase.update(DATABASE_TABLE, newValues, KEY_ID + " = ?", new String[] {Long.toString(_rowIndex)});
		return;
	}
	
	
	public Cursor filter(String constraint){
		return myDataBase.query(DATABASE_TABLE, new String[] {KEY_ID, LOCATION, STOP_ID}, LOCATION + " LIKE '%" + constraint + "%'", null, null, null, LOCATION + " ASC");
	}
	
	
	//TODO is this even needed?!?! ...yes
	public Stop getStop(long _rowIndex){
		//query the database for the stop_id and location, put it in a stop object
		Cursor mCursor = myDataBase.query(DATABASE_TABLE, new String[] {LOCATION, STOP_ID}, KEY_ID + "= ?", new String[] {Long.toString(_rowIndex)}, null, null, null );
		//mCursor.getColumnIndexOrThrow(STOP_ID);
		mCursor.moveToFirst();
		
		/** Note to self, if you change the db, you have to change how it gets values! (It is the order you query) **/
		return new Stop().setStopID(mCursor.getInt(1)).setStopLocation(mCursor.getString(0));
	}
 
}