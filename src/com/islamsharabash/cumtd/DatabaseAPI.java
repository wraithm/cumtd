package com.islamsharabash.cumtd;

import java.io.IOException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// provides a high level api for use within the rest of the application
// only one instance exists per application to ensure no race conditions
public class DatabaseAPI {
	
	private static DatabaseAPI instance = null;
	private SQLiteDatabase database; 

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
	
	// get longitude and latitude for stop (needed???)
	
	// get stops within certain lat/long bound
	
	

}

