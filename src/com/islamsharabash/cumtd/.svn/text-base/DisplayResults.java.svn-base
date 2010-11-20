package com.islamsharabash.cumtd;

import java.io.IOException;

import com.islamsharabash.cumtd.CumtdSearch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;


public class DisplayResults extends Activity {
	final Context ctx = this;
	
	/** Called when activity is first created **/
	
	//Create handler for UI updates
	Handler UIHandler = new Handler(){
	public void handleMessage(Message msg){
		super.handleMessage(msg);
		
		Bundle resultsBundle = msg.getData();
		String[] results = resultsBundle.getStringArray("results");
		
		//results[0] should now have title, and results[1] should now have text
		
		///message stuff
    	TextView tv = (TextView) findViewById(R.id.ResultsTextView01);
    	tv.setText(results[0]+results[1]);		
	}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Stop stop;
		
		//Set the view
		setContentView(R.layout.displayresults);

		//set the loading text	
    	TextView tv = (TextView) findViewById(R.id.ResultsTextView01);
    	tv.setText(R.string.load);
		
    	
    	//get the intent, and open up the bundle
		Intent i = this.getIntent();
		Bundle stopBundle = i.getBundleExtra("com.islamsharabash.cumtd.stop"); 
		stop = (Stop) stopBundle.getSerializable("stop");
		
       //Do the search in a new task to prevent slowdown
		Runnable searchJob = new Search(stop);
		Thread freshThread = new Thread (searchJob);
		
		freshThread.start();
     
		
        
        //Refresh functionality
    	final Button button = (Button) findViewById(R.id.RefreshButton);
    	button.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			
    	    	TextView tv = (TextView) findViewById(R.id.ResultsTextView01);
    	    	tv.setText(R.string.load);
    	    	
    			//Do the search in a new task to prevent slowdown
    			new Thread(new Search(stop)).start();

    		}
    	});
        

		
	}//close onCreate
	


	

	
	public class Search implements Runnable  {
		Stop stop;
		
		//constructor requires text for stop
		Search (Stop _stop){
			stop = _stop;
		}

		public void run() {
			//create a search object
			CumtdSearch searchObject = new CumtdSearch(stop, ctx);
			/**
			 * IMPLEMENT EXCEPTION HANDLING HERE!!!
			 */
			try{
				searchObject.fetch();
				//update the UI
				updateUI(searchObject.getResults());
			} catch (IOException e) {
				//return "Invalid Stop Id"
				updateUI(new String[] {"Invalid Stop ID", ""});
			}
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
