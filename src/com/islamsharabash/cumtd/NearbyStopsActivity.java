package com.islamsharabash.cumtd;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NearbyStopsActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
			TextView textview = new TextView(this);
			textview.setText("Not done yet... want it faster? Buy me coffee.");
			setContentView(textview);
	    }

}
