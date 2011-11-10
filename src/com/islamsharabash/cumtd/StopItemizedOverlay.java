package com.islamsharabash.cumtd;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

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
		Intent display_results = new Intent(context, DisplaySearchResults.class);
		Bundle bundle = new Bundle();
    	Stop stop = ((StopOverlayItem) overlays.get(index)).getStop();;
        bundle.putSerializable("stop", stop);

        display_results.putExtra("com.islamsharabash.cumtd.stop", bundle);
        context.startActivity(display_results);
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
