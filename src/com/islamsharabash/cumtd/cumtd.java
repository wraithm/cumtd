package com.islamsharabash.cumtd;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.*;

// sets up tabs and intents
public class cumtd extends TabActivity {

	public static final int NEARBY_TAB = 0;
	public static final int LOOKUP_TAB = 1;
	public static final int FAVORITES_TAB = 2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create intents to launch activity per tab
	    
	    intent = new Intent().setClass(this, NearbyStopsActivity.class);
	    spec = tabHost.newTabSpec("nearby")
		       .setIndicator("Nearby", res.getDrawable(R.drawable.ic_menu_myplaces))
		       .setContent(intent);
	    tabHost.addTab(spec);
	    

	    intent = new Intent().setClass(this, LookupStopsActivity.class);
	    spec = tabHost.newTabSpec("lookup")
	    	   .setIndicator("Lookup", res.getDrawable(R.drawable.ic_btn_search))
	    	   .setContent(intent);
	    tabHost.addTab(spec);	    

	    
	    intent = new Intent().setClass(this, FavoritesActivity.class);
	    spec = tabHost.newTabSpec("favorites")
	    	   .setIndicator("Favorites", res.getDrawable(R.drawable.btn_star))
	           .setContent(intent);
	    tabHost.addTab(spec);
	    
	    
	    // start app on favorites tab
	    tabHost.setCurrentTab(FAVORITES_TAB);
	}
}