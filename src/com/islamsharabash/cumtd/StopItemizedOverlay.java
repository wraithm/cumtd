package com.islamsharabash.cumtd;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.*;


public class StopItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
	private Context context;
	
	public StopItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		context = mapView.getContext();
	}
	
	@Override
	protected boolean onBalloonTap(int index) {
    	Stop stop = ((StopOverlayItem) overlays.get(index)).getStop();;
		DeparturesActivity.launchForStop(stop, context);
		return true;
	}

	public void addOverlay(OverlayItem overlay) {
	    overlays.add(overlay);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return overlays.get(i);
	}

	@Override
	public int size() {
		return overlays.size();
	}
}
