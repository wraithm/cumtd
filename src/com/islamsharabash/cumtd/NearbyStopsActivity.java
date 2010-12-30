package com.islamsharabash.cumtd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.*;
import com.islamsharabash.cumtd.StopMapView.StopMapListener;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class NearbyStopsActivity extends MapActivity {
	
	List<Overlay> mapOverlays;
	Drawable busDrawable;
	Drawable clusterDrawable;
	
	StopItemizedOverlay stopOverlay;
	
	final DataBaseHelper db = new DataBaseHelper(NearbyStopsActivity.this);
	MyMap myMap = null;
	MyLocationOverlay myLocation = null;
	MapController mapController = null;
	StopMapView stopMapView = null;
	ImageButton myLocBtn = null;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.nearbystops);
			
			setupDB();

			// setup the mapView, button, and my location

			myMap = (MyMap) findViewById(R.id.mymap);
			
			myLocBtn = myMap.getLocBtn();
			myLocBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showNearStops();
				}
			});
			
			stopMapView = myMap.getStopMap();	
			stopMapView.addStopMapListener(mListener);
			
			mapController = stopMapView.getController();
			stopMapView.setBuiltInZoomControls(true);
			myLocation = new MyLocationOverlay(this, stopMapView);
						
			mapOverlays = stopMapView.getOverlays();
			
			busDrawable = this.getResources().getDrawable(R.drawable.bus);
			clusterDrawable = this.getResources().getDrawable(R.drawable.buscluster);
			
	    }

	
	/**
	 * @param lat the latitude span we want to draw stops for
	 * @param lng the longitude span we want to draw stops for
	 * @param g a geopoint with where we want to center our stops around
	 */
	private void drawStops(int lat, int lng, GeoPoint g) {
		
		// does a bounds search to see how many stops are within bounds
		Cursor mCursor = db.boundStops(
				g.getLatitudeE6() + (lat/2),
				g.getLatitudeE6() - (lat/2),
				g.getLongitudeE6() + (lng/2),
				g.getLongitudeE6() - (lng/2));
		
		stopOverlay = new StopItemizedOverlay(busDrawable, stopMapView);
		
		mapOverlays.clear();
		stopMapView.postInvalidate();
	
		if (mCursor.getCount() > 25) {
			Toast.makeText(getBaseContext(), "Zoom in to see stops", 75).show();
			mCursor.close();
		} else {
			addStops(mCursor);
		}
		
		
		if (stopOverlay.size() != 0)
			mapOverlays.add(stopOverlay);
	}
	

	private StopMapListener mListener = new StopMapListener() {

		@Override
		public void onMapPan(int lat, int lng, GeoPoint g) {
			drawStops(lat, lng, g);
			mapOverlays.add(myLocation);
		}
	
		@Override
		public void onZoomChange(int lat, int lng, GeoPoint g) {
			drawStops(lat, lng, g);
			mapOverlays.add(myLocation);
		}

	};

	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	
	/**
	 * @param mCursor cursor of stops that you want added
	 */
	private void addStops (Cursor mCursor) {
		
		// add it to the map
			ArrayList<Stop> mStops = db.allCursorToStops(mCursor);
			for(int i = 0; i < mStops.size(); i++){
				Stop mStop =  (Stop) mStops.get(i);
				StopOverlayItem myStopItem = new StopOverlayItem(mStop);
				stopOverlay.addOverlay(myStopItem);
			}
			
			stopOverlay.populateIt();
			mCursor.close();
	}
	
	
	private void setupDB() {
		try {
			db.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			db.openDataBase();
		}catch(SQLException sqle){
			throw sqle;
		}
  }
	
	  
	@Override
	protected void onResume() {
		super.onResume();
		myLocation.enableMyLocation();
		drawStops(stopMapView.getLongitudeSpan(),
				stopMapView.getLongitudeSpan(),
				stopMapView.getMapCenter());
	}
	
	private void showNearStops() {
		GeoPoint mLocation = null;
		
		mLocation = myLocation.getMyLocation();
		
		if (mLocation == null) {

			LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			Location lastKnownLoc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			if (lastKnownLoc != null){
				int longTemp = (int)(lastKnownLoc.getLongitude()* 1000000);
				int latTemp = (int)(lastKnownLoc.getLatitude() * 1000000);
				mLocation =  new GeoPoint(latTemp, longTemp);
			}
		}
		
		if (mLocation != null) {
			mapController.animateTo(mLocation);
			mapController.zoomToSpan(2169, 2839);
			drawStops(stopMapView.getLatitudeSpan(), stopMapView.getLongitudeSpan(), mLocation);
			mapOverlays.add(myLocation);
		} else {
			Toast.makeText(getBaseContext(), "Your location could not be found", 75).show();
		}
	}
	  
	  
	@Override
	protected void onPause() {
		super.onPause();
		myLocation.disableMyLocation();
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
	
}
