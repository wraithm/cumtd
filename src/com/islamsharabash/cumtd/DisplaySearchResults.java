package com.islamsharabash.cumtd;

/**
 * DisplaySearchResults shows when the bus will get to a certain time
 * 
 * TODO:
 * 		We want to display a bus icon with the color of the line
 * 		the amount of time until arrival
 * 		a button next to the time to view the route... maybe the bus icon? (should be w/e route icon is)
 */

import com.islamsharabash.cumtd.CumtdSearch;

import java.io.IOException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
		String[] results = resultsBundle.getStringArray("results");
		loading.setVisibility(4);
		///message stuff
    	resultsTV.setText(results[0]+results[1]);		
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
		new Search().execute();
	}

	
	private class Search extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			CumtdSearch searchObject = new CumtdSearch(cStop, ctx);
			try{
				searchObject.fetch();
				//update the UI
				updateUI(searchObject.getResults());
			} catch (IOException e) {
				//return "Invalid Stop Id"
				updateUI(new String[] {"Invalid Stop ID", ""});
			}
			return null;
		}

	}//end Search
	
	private void updateUI(String[] results){
		//create a bundle with data, a message with the UIHandler, and send it
		Bundle resultsBundle = new Bundle();
		resultsBundle.putStringArray("results", results);
		Message resultsMessage = Message.obtain(UIHandler);
		resultsMessage.setData(resultsBundle);
		resultsMessage.sendToTarget();
	}//end updateUI
	
	
}
