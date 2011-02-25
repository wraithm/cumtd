package com.islamsharabash.cumtd;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.*;

public class cumtd extends TabActivity {

	public static final int LOOKUPSTOPSACTIVITY = 0;
	public static final int FAVORITESACTIVITY = 1;
	public final DataBaseHelper db = new DataBaseHelper(cumtd.this);
	SharedPreferences mPrefs;
	int mVersion = 13;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    // setup preferences
	    setPreferences();
	    
	    //check for first version and show dialog
	    if (getFirstRun()) {
	    	
	    	// show dialog box, or do we/e
	    	showMessage();
	    	
	    	// set the version
	    	setVersion();
	    }
	 
	    
	    setupDB();
	    
		
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, NearbyStopsActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("nearby").setIndicator("Nearby",
            	res.getDrawable(R.drawable.ic_menu_myplaces))
                .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, LookupStopsActivity.class);
	    spec = tabHost.newTabSpec("lookup").setIndicator("Lookup",
            	res.getDrawable(R.drawable.ic_btn_search))
                .setContent(intent);
	    tabHost.addTab(spec);	    

	    intent = new Intent().setClass(this, FavoritesActivity.class);
	    spec = tabHost.newTabSpec("favorites").setIndicator("Favorites",
	             	res.getDrawable(R.drawable.btn_star))
	             .setContent(intent);
	    tabHost.addTab(spec);

	    // change this based upon preferences possibly
	    tabHost.setCurrentTab(2);
	}
	

	private void showMessage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Note:")
				.setMessage("This is an unofficial mtd application, it is not sponsored nor endorsed by mtd.\n\n" +
						"If you need support contact Islam Sharabash at:\n islam.sharabash@gmail.com")
		       .setCancelable(true);
		builder.show();
	}
	
	/**
	 * get if this is the first run of this version
	 *
	 * @return returns true, if this is the first run
	 */
	 public boolean getFirstRun() {
	    	if (mPrefs.getInt("version", 0) != mVersion)
	    		return true;
	    	else
	    		return false;
	 }
	 
	 /**
	 * store the version
	 */
	 public void setVersion() {
	    SharedPreferences.Editor edit = mPrefs.edit();
	    edit.putInt("version", mVersion);
	    edit.commit();
	 }
	  
	 /**
	 * setting up preferences storage
	 */
	 public void setPreferences() {
	    Context mContext = this.getApplicationContext();
	    mPrefs = mContext.getSharedPreferences("cumtdPrefs", 0); //0 = mode private. only this app can read these preferences
	 }
	
	
	
	  private void setupDB() {
		  try {
		  db.createDataBase();
		  } catch (IOException ioe) {
			  throw new Error("Unable to create database");
		  }

		  try {
		  db.openDataBase();
		  }catch(SQLException sqle){
			  throw sqle;
		  }
	  }
	  

	
}