package com.islamsharabash.cumtd;

/**
 * DisplaySearchResults shows when the bus will get to a certain time
 * 
 * TODO:
 * 		We want to display a bus icon with the color of the line
 * 		the amount of time until arrival
 * 		a button next to the time to view the route... maybe the bus icon? (should be w/e route icon is)
 */

import java.io.IOException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;


public class DeparturesActivity extends Activity {

	ProgressBar loadingBar;
	TextView resultsView;
	Stop stop;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.departures);
		
		initUI();
		
		// get stop info
		Intent intent = this.getIntent();
		Bundle stopBundle = intent.getBundleExtra("com.islamsharabash.cumtd.stop");
		stop = (Stop) stopBundle.getSerializable("stop");
		
		// start an async request for results (also updates ui)
		new GetBusTimes().execute(stop);
	}
	
	private void initUI() {
		loadingBar = (ProgressBar) findViewById(R.id.loading);
		resultsView = (TextView) findViewById(R.id.ResultsTextView01);
		resultsView.setMovementMethod(new ScrollingMovementMethod());
		
        //Refresh functionality
    	Button button = (Button) findViewById(R.id.RefreshButton);
    	button.setOnClickListener(new View.OnClickListener() {
    		@Override
			public void onClick(View v) {
				new GetBusTimes().execute(stop);
    		}
    	});
	}
	
	// sets the loading, gets the search results, and displays them
	// the third param (string) for asynctask is the type passed to onpostexecute
	private class GetBusTimes extends AsyncTask<Stop, Void, String> {
		protected void onPreExecute() {
			loadingBar.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected String doInBackground(Stop... params) {
			CumtdAPI api = new CumtdAPI();
			try {
				return api.getDeparturesByStop(stop);
			} catch (IOException e1) {
				return "Connection error";
			}
		}
		
		protected void onPostExecute(String result) {
	    	resultsView.setText(stop.getName() + "\n" + result);		
			loadingBar.setVisibility(View.INVISIBLE);
		}
	}
	
	public static void launchForStop(Stop stop, Context context) {
		Intent display_results = new Intent(context, DeparturesActivity.class);
		Bundle bundle = new Bundle();
        bundle.putSerializable("stop", stop);

        display_results.putExtra("com.islamsharabash.cumtd.stop", bundle);
        context.startActivity(display_results);
	}
}
