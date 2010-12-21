package com.islamsharabash.cumtd;

import java.io.Serializable;


/**
 * @author Islam Sharabash
 * Object to encapsulate Stop id, location name, and physical location
 * Can be extended to hold other information about the stop later
 */
public class Stop implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String name;
	private int stopID;
	private double latitude;
	private double longitude;
	
	public Stop() {
		name = "undefined name";
		stopID = 0000;
		latitude = 0;
		longitude = 0;
	}
	
	public Stop(int _stopID) {
		stopID = _stopID;
		name = "undefined name";
		latitude = 0;
		longitude =0;
	}
	
	public Stop(int _stopID, String _name) {
		stopID = _stopID;
		name = _name;
		latitude = 0;
		longitude =0;
	}
	
	public Stop(int _stopID, String _name, double _latitude, double _longitude) {
		stopID = _stopID;
		name = _name;
		latitude = _latitude;
		longitude = _longitude;
	}
	
	
	public Stop setStopID(int _stopID) {
		stopID = _stopID;
		return this;
	}
	
	public Stop setName(String _name) {
		name = _name;
		return this;
	}
	
	public Stop setLatitude(Double _latitude) {
		latitude = _latitude;
		return this;
	}
	
	public Stop setLongitude(Double _longitude) {
		latitude = _longitude;
		return this;
	}
	
	public int getStopID(){
		return stopID;
	}
	
	public String getName(){
		return name;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}

}
