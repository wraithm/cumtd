package com.islamsharabash.cumtd;

import java.util.List;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;


//TODO make progress bar above list
public class DeparturesActivity extends ListActivity {

	ProgressBar loadingBar;
	Stop stop;
	DepartureAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.departures);
		
		// get stop info
		Intent intent = this.getIntent();
		Bundle stopBundle = intent.getBundleExtra("com.islamsharabash.cumtd.stop");
		stop = (Stop) stopBundle.getSerializable("stop");
			
		adapter = new DepartureAdapter(this);
		setListAdapter(adapter);
		
		initUI();
		

		// start an async request for results (also updates ui)
		new GetBusTimes().execute(stop);
	}
	
	private void initUI() {
		loadingBar = (ProgressBar) findViewById(R.id.loading);
		TextView stop_name = (TextView) findViewById(R.id.StopName);
    	stop_name.setText(stop.getName());		
		
        //Refresh functionality
    	ImageButton button = (ImageButton) findViewById(R.id.Refresh);
    	button.setOnClickListener(new View.OnClickListener() {
    		@Override
			public void onClick(View v) {
				new GetBusTimes().execute(stop);
    		}
    	});
	}
	
	// sets the loading, gets the search results, and updates the adapter with it
	// the third param (string) for asynctask is the type passed to onpostexecute
	private class GetBusTimes extends AsyncTask<Stop, Void, List<Departure>> {
		private Exception exception = null;
		protected void onPreExecute() {
			loadingBar.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected List<Departure> doInBackground(Stop... stops) {
			CumtdAPI api = new CumtdAPI();
			
			Stop stop = stops[0];
			try {
				return api.getDeparturesByStop(stop);
			} catch (Exception e) {
				exception = e;
				Log.e("Departure failure", e.getMessage());
			}
			return null;
		}
		
		protected void onPostExecute(List<Departure> departures) {
			if (exception != null) {
				//TODO oh shit, we got an exception, notify the user with a toast or something depending on what happened...
				return;
			}
			
			adapter.setDepartures(departures);
			adapter.notifyDataSetChanged();
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
