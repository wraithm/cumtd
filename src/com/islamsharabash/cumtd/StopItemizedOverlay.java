package com.islamsharabash.cumtd;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.*;


public class StopItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext = null;
	
	public StopItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		mContext = mapView.getContext();
	}
	
	@Override
	protected boolean onBalloonTap(int index) {
		
		 Intent i = new Intent(mContext, DisplaySearchResults.class);
    	 Bundle stopBundle = new Bundle();
    	 Stop mStop = ((StopOverlayItem) mOverlays.get(index)).stop;
         stopBundle.putSerializable("stop", mStop);

         i.putExtra("com.islamsharabash.cumtd.stop", stopBundle);
         mContext.startActivity(i);
		return true;
	}

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	public void populateIt() {
		populate();
	}
	
	
}
