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
		
			TextView textView = new TextView(this);
			/**
			textview.setText("There are two ways this can go: \n \n" +
					"1) You enter a destination and I get results from google transit (which would tell you which buses to take) I and let you \"favorite\" destinations \n \n" +
					"2) I list different bus lines and when you click one it displays a picture of that route \n \n" +
					"Email your preference to: \n"+"islam.sharabash@gmail.com");
					**/
			textView.setTextSize(16);
			textView.setText("Nearby stops is a work in progess \n (if you can't see anything, try zooming in)\n \n" +
							"Email feedback to islam.sharabash@gmail.com");
			setContentView(textView);
	    }

}
