package com.islamsharabash.cumtd;

import com.islamsharabash.cumtd.StopMapView.StopMapListener;
import com.islamsharabash.cumtd.StopMapView.TouchListener;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class MyMap extends FrameLayout {
	
	private ImageButton myLocBtn = null;
	private StopMapView stopMapView = null;
	private Context mContext = null;

	public MyMap(Context context) {
		super(context);
		mContext = context;
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.mymap, this, true);
		
		// Get references to the child controls.
		myLocBtn = (ImageButton)findViewById(R.id.mylocationbtn);
		stopMapView = (StopMapView)findViewById(R.id.stopmapview);
		stopMapView.addTouchListener(tListener);
	}
	
	public MyMap(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.mymap, this, true);
		
		// Get references to the child controls.
		myLocBtn = (ImageButton)findViewById(R.id.mylocationbtn);
		stopMapView = (StopMapView)findViewById(R.id.stopmapview);
		stopMapView.addTouchListener(tListener);
	}
	
	public MyMap(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.mymap, this, true);
		
		// Get references to the child controls.
		myLocBtn = (ImageButton)findViewById(R.id.mylocationbtn);
		stopMapView = (StopMapView)findViewById(R.id.stopmapview);
		stopMapView.addTouchListener(tListener);
	}
	
	public ImageButton getLocBtn() {
		return myLocBtn;
	}
	
	public StopMapView getStopMap() {
		return stopMapView;
	}

	public void addStopMapListener(StopMapListener l) {
		stopMapView.addStopMapListener(l);
	}
	
	
	private void fadeInMyLocButton() {
		Animation a = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
		a.reset();
		myLocBtn.clearAnimation();
		myLocBtn.startAnimation(a);
		myLocBtn.setVisibility(VISIBLE);
	}
	
	
	private void fadeOutMyLocButton() {
		Animation a = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
		a.reset();		
		myLocBtn.clearAnimation();
		myLocBtn.startAnimation(a);
		myLocBtn.setVisibility(INVISIBLE);
	}
	
	private Handler handler = new Handler();
	
	TouchListener tListener = new TouchListener() {

		@Override
		public void onTouchEvent(MotionEvent ev) {
			if (ev.getAction()==MotionEvent.ACTION_DOWN) {
				fadeInMyLocButton();
			}
			
			if (ev.getAction()==MotionEvent.ACTION_UP) {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						fadeOutMyLocButton();
					}
				}, 2500);
			}
		}		
	};
}
