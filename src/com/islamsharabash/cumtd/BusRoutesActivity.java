package com.islamsharabash.cumtd;

/**
 * BusRoutesAcitvity lists bus lines as button for users to click and see route
 * TODO:
 * 		Should notify users of reroutes
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class BusRoutesActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
			TextView textview = new TextView(this);
			textview.setText("Not done yet... want it faster? Buy me coffee.");
			setContentView(textview);
	    }

}
