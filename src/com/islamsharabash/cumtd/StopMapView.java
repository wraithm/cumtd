package com.islamsharabash.cumtd;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class StopMapView extends MapView {
	
	 int oldZoomLevel = -1;
	 int oldLatSpan = -1;
	 int oldLngSpan = -1;
	 GeoPoint oldMapCenter = new GeoPoint(0, 0);
	 
	private Vector<StopMapListener> mListeners = new Vector<StopMapListener>();
	private Vector<TouchListener> tListeners = new Vector<TouchListener>();


	public StopMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StopMapView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public StopMapView(Context context, String apiKey) {
		super(context, apiKey);
	}
	
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction()==MotionEvent.ACTION_UP) {
		   //do your thing
			if(mapPanned()){
				// call all listeners
				
				for(int i = 0; i < mListeners.size(); i++) {
					StopMapListener target = null;
					target = (StopMapListener) mListeners.get(i);
					target.onMapPan(getLatitudeSpan(), getLongitudeSpan(), getMapCenter());
				}
				
			}
		 }
		
		for(int i = 0; i < tListeners.size(); i++) {
			TouchListener target = null;
			target = (TouchListener) tListeners.get(i);
			target.onTouchEvent(ev);
		}
		
		
		 return super.onTouchEvent(ev);
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

	public void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (getZoomLevel() != oldZoomLevel) {
			// call all listeners
			
			for(int i = 0; i < mListeners.size(); i++) {
				StopMapListener target = null;
				target = (StopMapListener) mListeners.get(i);
				target.onZoomChange(getLatitudeSpan(), getLongitudeSpan(), getMapCenter());
			}
			
			oldZoomLevel = getZoomLevel();
		}
	}
	
	public void addStopMapListener(StopMapListener l) {
		mListeners.add(l);
	}
	
	public void addTouchListener(TouchListener l) {
		tListeners.add(l);
	}
	
	
	public interface TouchListener {
		public void onTouchEvent(MotionEvent ev);
	}
	
	public interface StopMapListener {
		/**
		 * Called when the map is panned
		 * @param lat the latitude span of the map
		 * @param lng the longitude span of the map
		 * @param g GeoPoint with the center of the map
		 */
		public void onMapPan(int lat, int lng, GeoPoint g);
		
		/**
		 * Called when the zoom has changed
		 * @param lat the latitude span of the map
		 * @param lng the longitude span of the map
		 * @param g GeoPoint with the center of the map
		 */
		public void onZoomChange(int lat, int lng, GeoPoint g);	
	}
	
}
