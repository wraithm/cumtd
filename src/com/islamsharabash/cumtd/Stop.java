package com.islamsharabash.cumtd;

import java.io.Serializable;


/**
 * @author Islam Sharabash
 * Object to encapsulate Stop id, location name, and physical location
 */
public class Stop implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String stopID;
	private int latitude;
	private int longitude;
	private boolean favorite;
	
	public Stop(String _stopID, String _name, int _latitude, int _longitude, boolean _favorite) {
		stopID = _stopID;
		name = _name;
		latitude = _latitude;
		longitude = _longitude;
		favorite = _favorite;
	}
	
	public String getName() {
		return name;
	}
	
	public String getID() {
		return stopID;
	}
	
	public int getLatitude() {
		return latitude;
	}
	
	public int getLongitude() {
		return longitude;
	}
	
	public boolean isFavorite() {
		return favorite;
	}
	
	public String toString() {
		return stopID + " " + name + "; lat:" + latitude + "; lng:" + longitude + "; fav:" + favorite;
	}
}