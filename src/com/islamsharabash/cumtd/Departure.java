package com.islamsharabash.cumtd;

import android.text.format.Time;

//TODO this will depend on the new version of the api (to be released within a week of 11/15)
// TODO hold out for this: http://developer.cumtd.com/forum/default.aspx?g=posts&m=89&#post89
public class Departure {
	
	private String route;
	private Time time;
	
	// raw time is the time returned by JSON, which is formatted
	public Departure(String route, String raw_time) {
		this.route = route;
		this.time = parse(raw_time);
	}
	
	private Time parse(String raw_time) {
		//TODO write this function
		return time;
	}
	
	// int returned should be a color, maybe use resources?
	public int getRouteColor() {
		//TODO implement this
		return 0;
	}
	
	public String getRoute() {
		return route;
	}
	
	public String getTimeTilDeparture() {
		//TODO implement this
		return "";
	}
	
	public int getTimeColor() {
		//TODO implement this
		return 0;
	}
	

}
