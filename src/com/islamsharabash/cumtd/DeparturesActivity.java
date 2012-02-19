package com.islamsharabash.cumtd;

import java.util.List;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
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
		
        // Refresh functionality
    	ImageButton button = (ImageButton) findViewById(R.id.Refresh);
    	button.setOnClickListener(new View.OnClickListener() {
    		@Override
			public void onClick(View v) {
				new GetBusTimes().execute(stop);
    		}
    	});
    	
    	// Favorite functionality
    	CheckBox star = (CheckBox) findViewById(R.id.Star);
		star.setChecked(stop.isFavorite());
    	star.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View view) {
    			stop.toggleFavorite();
    		}
    	});
	}
	
	// sets the loading, gets the search results, and updates the adapter with it
	// the third param (string) for asynctask is the type passed to onpostexecute
	private class GetBusTimes extends AsyncTask<Stop, Void, List<Departure>> {
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
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(List<Departure> departures) {
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
