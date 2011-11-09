package com.islamsharabash.cumtd;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class TouchMapView extends MapView {
	
	public TouchMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	int oldZoomLevel = -1;
	int oldLatSpan = -1;
	int oldLngSpan = -1;
	GeoPoint oldMapCenter = new GeoPoint(0, 0);
	 
	private TouchMapListener listener = null;

	public void setListener(TouchMapListener _listener) {
		this.listener = _listener;
	}
	
	public boolean onTouchEvent(MotionEvent ev) {
		boolean isMapPan = 
			(listener != null) &&
			(ev.getAction() == MotionEvent.ACTION_UP) &&
			mapPanned();
		
		if (isMapPan) {
			this.listener.onMapPan();
		}
		
		return super.onTouchEvent(ev);
	}
	
	public void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (getZoomLevel() != oldZoomLevel) {
			oldZoomLevel = getZoomLevel();
			this.listener.onZoomChange();
		}
	}
		
	private boolean mapPanned() {
		if (
			(oldLatSpan != getLatitudeSpan()) ||
			(oldLngSpan != getLongitudeSpan()) ||
			(oldMapCenter.getLatitudeE6() != getMapCenter().getLatitudeE6()) ||
			(oldMapCenter.getLongitudeE6() != getMapCenter().getLongitudeE6())) {
			
			oldLatSpan = getLatitudeSpan();
			oldLngSpan = getLongitudeSpan();
			oldMapCenter = getMapCenter();		
			return true;
		}
		
		return false;
	}

	public interface TouchMapListener {
		// Called when the map is panned
		public void onMapPan();
		
		// Called when the zoom has changed
		public void onZoomChange();	
	}
}
