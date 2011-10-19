package com.islamsharabash.cumtd;

import android.app.Application;
import android.content.Context;

// hack from http://stackoverflow.com/questions/2002288/static-way-to-get-context-on-android
public class cumtdApplication extends Application{
	
	private static Context context;
	
	public void onCreate() {
		cumtdApplication.context = getApplicationContext(); 
		super.onCreate();
	}
	
	public static Context getAppContext() {
		return cumtdApplication.context;
	}

}
