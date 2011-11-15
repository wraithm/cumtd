package com.islamsharabash.cumtd;

import java.util.List;
import com.google.android.maps.*;
import com.islamsharabash.cumtd.TouchMapView.TouchMapListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

public class NearbyStopsActivity extends MapActivity {
	
	List<Overlay> mapOverlays;
	Drawable busDrawable;
	
	DatabaseAPI db = DatabaseAPI.getInstance();
	TouchMapView touchMap;
	
	// approx location is green and wright
	int START_LAT = 40109995;
	int START_LONG = -88229110;
	int START_ZOOM = 16;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.nearbystops);
			
			// setup the mapView, button, and my location

			touchMap = (TouchMapView) findViewById(R.id.touchmap);
			
			touchMap.setListener(listener);
			
			// set init position
			MapController controller = touchMap.getController();
			controller.setCenter(new GeoPoint(START_LAT, START_LONG));
			controller.setZoom(16);
			
			touchMap.setBuiltInZoomControls(true);
			
			mapOverlays = touchMap.getOverlays();
			
			busDrawable = this.getResources().getDrawable(R.drawable.bus);
	    }

	
	/**
	 * @param lat the latitude span we want to draw stops for
	 * @param lng the longitude span we want to draw stops for
	 * @param g a geopoint with where we want to center our stops around
	 */
	private void drawStops() {
		GeoPoint center = touchMap.getMapCenter();
		int lat_span = touchMap.getLatitudeSpan();
		int long_span = touchMap.getLongitudeSpan();
		
		int lat_lower = center.getLatitudeE6() - (lat_span/2);
		int lat_upper = center.getLatitudeE6() + (lat_span/2);
		int long_lower = center.getLongitudeE6() - (long_span/2);
		int long_upper = center.getLongitudeE6() + (long_span/2);
		
		List<Stop> stops = db.getStopsWithin(lat_upper, lat_lower, long_upper, long_lower);
		
		if (stops.size() == 0) {
			return;
		}
		
		StopItemizedOverlay stopOverlays = new StopItemizedOverlay(busDrawable, touchMap);
		
		mapOverlays.clear();
		touchMap.postInvalidate();
	
		if (stops.size() > 25) {
			Toast.makeText(getBaseContext(), "Zoom in to see stops", 75).show();
			return;
		}
		
		// add overlays
		for (Stop stop : stops) {
			StopOverlayItem stop_item = new StopOverlayItem(stop);
			stopOverlays.addOverlay(stop_item);
		}
		mapOverlays.add(stopOverlays);
	}
	
	private TouchMapListener listener = new TouchMapListener() {
		@Override
		public void onMapPan() {
			drawStops();
		}
	
		@Override
		public void onZoomChange() {
			drawStops();
		}
	};

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		//drawStops();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
