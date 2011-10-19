package com.islamsharabash.cumtd;

/**
 * DisplaySearchResults shows when the bus will get to a certain time
 * 
 * TODO:
 * 		We want to display a bus icon with the color of the line
 * 		the amount of time until arrival
 * 		a button next to the time to view the route... maybe the bus icon? (should be w/e route icon is)
 */

import java.util.Iterator;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;


public class DisplaySearchResults extends Activity {

	final Context ctx = this;
	Stop cStop = null;
	ProgressBar loading = null;
	TextView resultsTV = null;
	
	//Create handler for UI updates
	Handler UIHandler = new Handler(){
	@Override
	public void handleMessage(Message msg){
		super.handleMessage(msg);
		
		Bundle resultsBundle = msg.getData();
		String results = resultsBundle.getString("results");
		loading.setVisibility(4);
		///message stuff
    	resultsTV.setText(cStop.getName()+"\n"+results);		
	}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Set the view
		setContentView(R.layout.displayresults);
    	
    	//get the intent, and open up the bundle
		Intent i = this.getIntent();
		Bundle stopBundle = i.getBundleExtra("com.islamsharabash.cumtd.stop");
		cStop = (Stop) stopBundle.getSerializable("stop");
		loading = (ProgressBar) findViewById(R.id.loading);
		resultsTV = (TextView) findViewById(R.id.ResultsTextView01);
		resultsTV.setMovementMethod(new ScrollingMovementMethod());
		
		loading.setVisibility(4);
		getResults();
     
        //Refresh functionality
    	final Button button = (Button) findViewById(R.id.RefreshButton);
    	button.setOnClickListener(new View.OnClickListener() {
    		@Override
			public void onClick(View v) {
    			getResults();
    		}
    	});
        	
	}//close onCreate
	
	private void getResults() {
    	loading.setVisibility(0);
    	cStop.results.clear();
		new Search().execute();
	}

	
	private class Search extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			CumtdSearch searchObject = new CumtdSearch(cStop, ctx);
			searchObject.getTimes();
			
			String allResults = "";
			
			for (Iterator<Bus> it = cStop.results.iterator(); it.hasNext(); ) {
				allResults += it.next().toString() + "\n";
			}

			updateUI(allResults);

			return null;
		}

	}//end Search
	
	private void updateUI(String results){

		//create a bundle with data, a message with the UIHandler, and send it
		Bundle resultsBundle = new Bundle();
		resultsBundle.putString("results", results);
		Message resultsMessage = Message.obtain(UIHandler);
		resultsMessage.setData(resultsBundle);
		resultsMessage.sendToTarget();
	}//end updateUI
	
	
}
