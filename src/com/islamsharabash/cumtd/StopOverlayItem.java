package com.islamsharabash.cumtd;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class StopOverlayItem extends OverlayItem {
	public Stop stop = null;
	
	// never use this
	public StopOverlayItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
		stop = new Stop();
	}
	
	public StopOverlayItem(Stop _stop) {
		
		super(new GeoPoint(_stop.getLatitude(),
							_stop.getLongitude()),
							_stop.getName(),
							"Tap to see bus times");
		stop = _stop;
		
		((StopOverlayItem)this).stop = _stop;

	}
};