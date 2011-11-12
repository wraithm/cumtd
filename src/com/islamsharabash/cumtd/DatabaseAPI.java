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
	private static String stopID = "_stop";
	private static String name = "_name";
	private static String favorite = "_favorite";
	private static String latitude = "_latitude";
	private static String longitude = "_longitude";

	public static DatabaseAPI getInstance() {
		if (DatabaseAPI.instance == null) {
			DatabaseAPI.instance = new DatabaseAPI();
		}
		
		return DatabaseAPI.instance;
	}
	// sets up the database s.t. we can use the api
	private DatabaseAPI() {
		DatabaseHelper helper = new DatabaseHelper(CumtdApplication.getAppContext());
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

	public List<Stop> getQuery(String query) {
		Cursor cursor = database.rawQuery(query, null);
		List<Stop> stops = cursorToStopList(cursor);
		cursor.close();
		return stops;
	}
	
	// get list of stops by search string (checks for empty too)
	public List<Stop> getStops(String search) {
		String query = "SELECT * from " + tableName +
					   " WHERE " + name +
					   " LIKE '%" + search + "%'";
		return getQuery(query);
	}
	
	// set a stop.favorite as isFavorite
	public void setFavorite(Stop stop, boolean isFavorite) {
		String query = "UPDATE OR ABORT " + tableName +
					    " SET " + favorite + " = " + isFavorite +
					    " WHERE " + stopID + " = " + stop.getID();
		
	}

	// get favorite stops
	public List<Stop> getFavoriteStops() {
		String query = "SELECT * from " + tableName +
					   " WHERE " + favorite + " > 0";
		return getQuery(query);
	}
	
	public List<Stop> getStopsWithin(int upper_lat, int lower_lat, int upper_long, int lower_long) {
		String query = "SELECT * from " + tableName +
					   " WHERE " + latitude +
					   " BETWEEN " + Integer.toString(lower_lat) + " AND " + Integer.toString(upper_lat) +
					   " AND " + longitude +
					   " BETWEEN " + Integer.toString(lower_long) + " AND " + Integer.toString(upper_long);
		return getQuery(query);
	}
	
	private List<Stop> cursorToStopList(Cursor cursor) {
		List<Stop> stops = new ArrayList<Stop>();
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			stops.add(new Stop(
				cursor.getString(1), // stop_id
				cursor.getString(2), // stop name
				cursor.getInt(3), // latitude
				cursor.getInt(4), // longitude
				(cursor.getInt(5) > 0) // favorite
			));
			cursor.moveToNext();
		}
		
		return stops;
	}
	
	

}

