package com.islamsharabash.cumtd;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class StopOverlayItem extends OverlayItem {
	public Stop stop = null;
	
	// never use this
	public StopOverlayItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
	}
	
	public StopOverlayItem(Stop stop) {
		
		super(
		  new GeoPoint(stop.getLatitude(), stop.getLongitude()),
		  stop.getName(),
		  "Tap to see bus times");
		
		this.stop = stop;
	}
};