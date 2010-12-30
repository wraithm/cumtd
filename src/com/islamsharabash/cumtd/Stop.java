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
	private int latitude;
	private int longitude;
	private boolean favorite;
	
	/**
	 * Constructors
	 */
	
	public Stop() {
		name = "undefined name";
		stopID = 0000;
		latitude = 0;
		longitude = 0;
		favorite = false;
	}
	
	public Stop(int _stopID) {
		stopID = _stopID;
		name = "undefined name";
		latitude = 0;
		longitude =0;
		favorite = false;
	}
	
	public Stop(int _stopID, String _name) {
		stopID = _stopID;
		name = _name;
		latitude = 0;
		longitude =0;
		favorite = false;
	}
	
	public Stop(int _stopID, String _name, int _latitude, int _longitude) {
		stopID = _stopID;
		name = _name;
		latitude = _latitude;
		longitude = _longitude;
		favorite = false;
	}
	
	public Stop(int _stopID, String _name, int _latitude, int _longitude, boolean _favorite) {
		stopID = _stopID;
		name = _name;
		latitude = _latitude;
		longitude = _longitude;
		favorite = _favorite;
	}
	
	
	/**
	 * A method!
	 */
	public String toString() {
		return stopID + " " + name + " lat:" + latitude + " lng:" + longitude + " fav:" + favorite;
	}
	
	
	/**
	 * Getters and Setters
	 * @param _stopID
	 * @return
	 */
	public Stop setStopID(int _stopID) {
		stopID = _stopID;
		return this;
	}
	
	public Stop setName(String _name) {
		name = _name;
		return this;
	}
	
	public Stop setLatitude(int _latitude) {
		latitude = _latitude;
		return this;
	}
	
	public Stop setLongitude(int _longitude) {
		latitude = _longitude;
		return this;
	}
	
	public Stop setFavorite(boolean _favorite) {
		favorite = _favorite;
		return this;
	}
	
	public int getStopID(){
		return stopID;
	}
	
	public String getName(){
		return name;
	}
	
	public int getLatitude() {
		return latitude;
	}
	
	public int getLongitude() {
		return longitude;
	}
	
	public boolean getFavorite() {
		return favorite;
	}

}
