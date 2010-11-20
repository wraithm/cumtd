package com.islamsharabash.cumtd;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import com.islamsharabash.cumtd.Stop;

public class CumtdDB extends SQLiteOpenHelper{
	/**
		We create a database variable for our database and interact with it using this class
		To create/open/upgrade the database we are going to use the open helper class
		We need to pass in the parent context so that we can return info to that context?
		Not sure how that works...
		We create final variables to hold database name, SQL statements, etc
		We create methods to interact with the database using those statements 
		
		!!"this" is a reference to the current object! Returning the "this" keyword allow pipelining of methods!
		
	**/
	
	//variables to hold the outer context, database instance, OpenHelper
	//Also variables to hold various database settings/text/createing databse
	private static final String DATABASE_NAME = "cumtdDB.db";
	private static final String DATABASE_TABLE ="stopTable";
	private static final String DATABASE_PATH = "/data/data/com.islamsharabash.cumtd/databases/";
	private static final int DATABASE_VERSION = 2;
	private static SQLiteDatabase db;
	
	//Database fields
	public static final String KEY_ID ="_id";
	public static final String STOP_ID="_stop";
	public static final String LOCATION="_loc";
	public static final String BOOKMARK="_book";
	
	
	
	
	
	
	public CumtdDB(Context _ctx) {
		 
    	super(_ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }	


	
    public void open() throws SQLException{
    	 
    	//Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(db != null)
    		    db.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 	
	
	//Meet and potatoes of our DB Adapter
	/****CREATE OBJECT CONTENT VALUES THING*****/
	/**MODIFY GET ALL TO REMOVE unnesccessary _id
	 * @throws MalformedStop **/
	
	public boolean saveBookmark(Stop _stop) throws MalformedStop{
		ContentValues newValues = new ContentValues();
		newValues.put(BOOKMARK, 1);		
		if( (db.update(DATABASE_TABLE, newValues, STOP_ID + " = ?", new String[] {Integer.toString(_stop.getStopID())})) == 1)
			return true;
		//fuck yeah... screw good code, throw a MalformedStop if it's not there!
		else throw new MalformedStop();			
	}
	
	public Cursor getAllEntries(){
		return db.query(DATABASE_TABLE, new String[] {KEY_ID, LOCATION, STOP_ID}, null, null, null, null, LOCATION + " ASC");
	}
	
	public Cursor getBookmarks(){
		return db.query(DATABASE_TABLE, new String[] {KEY_ID, LOCATION, STOP_ID}, BOOKMARK + " = 1", null, null, null, LOCATION + " ASC");
	}
	
	public void removeBookmark(long _rowIndex){
		ContentValues newValues = new ContentValues();
		newValues.put(BOOKMARK, 0);		
		db.update(DATABASE_TABLE, newValues, KEY_ID + " = ?", new String[] {Long.toString(_rowIndex)});
		return;
	}
	
	
	public Cursor filter(String constraint){
		return db.query(DATABASE_TABLE, new String[] {KEY_ID, LOCATION, STOP_ID}, LOCATION + " LIKE '%" + constraint + "%'", null, null, null, LOCATION + " ASC");
	}
	
	
	//TODO is this even needed?!?! ...yes
	public Stop getStop(long _rowIndex){
		//query the database for the stop_id and location, put it in a stop object
		Cursor mCursor = db.query(DATABASE_TABLE, new String[] {LOCATION, STOP_ID}, KEY_ID + "= ?", new String[] {Long.toString(_rowIndex)}, null, null, null );
		//mCursor.getColumnIndexOrThrow(STOP_ID);
		mCursor.moveToFirst();
		
		/** Note to self, if you change the db, you have to change how it gets values! (It is the order you query) **/
		return new Stop().setStopID(mCursor.getInt(1)).setStopLocation(mCursor.getString(0));
	}
	
	
	
}//close CumtdDB
	
	
	
	
	
	