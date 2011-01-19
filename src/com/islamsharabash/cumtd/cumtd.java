package com.islamsharabash.cumtd;

import java.io.IOException;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.*;

public class cumtd extends TabActivity {

	public static final int LOOKUPSTOPSACTIVITY = 0;
	public static final int FAVORITESACTIVITY = 1;
	public final DataBaseHelper db = new DataBaseHelper(cumtd.this);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
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
/**
	    intent = new Intent().setClass(this, BusRoutesActivity.class);
	    spec = tabHost.newTabSpec("routes").setIndicator("Routes",
	            	res.getDrawable(R.drawable.ic_dialog_map))
	            .setContent(intent);
	    tabHost.addTab(spec);
**/	    
	    intent = new Intent().setClass(this, FavoritesActivity.class);
	    spec = tabHost.newTabSpec("favorites").setIndicator("Favorites",
	             	res.getDrawable(R.drawable.btn_star))
	             .setContent(intent);
	    tabHost.addTab(spec);

	    // change this based upon preferences possibly
	    tabHost.setCurrentTab(2);
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