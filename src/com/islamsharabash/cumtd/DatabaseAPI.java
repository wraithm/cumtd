package com.islamsharabash.cumtd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// provides a high level api for use within the rest of the application
// only one instance exists per application to ensure no race conditions
public class DatabaseAPI {
	
	private static DatabaseAPI instance = null;
	private SQLiteDatabase database; 
	private static String tableName = "stopTable";
	private static String favorite = "_favorite";

	public static DatabaseAPI getInstance() {
		if (DatabaseAPI.instance == null) {
			DatabaseAPI.instance = new DatabaseAPI();
		}
		
		return DatabaseAPI.instance;
	}
	// sets up the database s.t. we can use the api
	private DatabaseAPI() {
		DatabaseHelper helper = new DatabaseHelper(cumtdApplication.getAppContext());
		try {
			helper.createDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("create database", "unable to create database");
			e.printStackTrace();
		}
		helper.openDatabase();
		database = helper.getDatabase();
	}

	
	// get list of stops by search string (checks for empty too)
	
	// set a stop as a favorite

	// get favorite stops
	public List<Stop> getFavoriteStops() {
		String selection = DatabaseAPI.favorite + " > 0";
		Cursor cursor = database.query(DatabaseAPI.tableName,
									   null, // all columns 
									   selection,
									   null, null, null, null);
		return cursorToStopList(cursor);
	}
	
	// get longitude and latitude for stop (needed???)
	
	// get stops within certain lat/long bound
	
	private List<Stop> cursorToStopList(Cursor cursor) {
		List<Stop> stops = new ArrayList<Stop>();
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			stops.add(new Stop(
				cursor.getString(2), // stop_id
				cursor.getString(3), // stop name
				cursor.getInt(4), // latitude
				cursor.getInt(5), // longitude
				(cursor.getInt(1) > 0) // favorite
			));
			cursor.moveToNext();
		}
		
		return stops;
	}
	
	

}

