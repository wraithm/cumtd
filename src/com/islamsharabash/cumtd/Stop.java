package com.islamsharabash.cumtd;

import java.io.Serializable;
import java.util.ArrayList;


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
	 * List of buses... going to be used for search results and such
	 */
	public ArrayList<Bus> results = new ArrayList<Bus>();
	
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

	public Stop(int _stopID, String _name) {
		stopID = _stopID;
		name = _name;
		latitude = 0;
		longitude =0;
		favorite = false;
	}
	
	public Stop(int _stopID, String _name, boolean _fav) {
		stopID = _stopID;
		name = _name;
		latitude = 0;
		longitude =0;
		favorite = _fav;
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
	
	public Stop setName(String _name) {
		name = _name;
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

}

/** 
 * @author islam
 *
 *	Bus object encapsulates routename and time til arrival
 */
class Bus {
	private String routeName = null;
	private String timeTillDepart = null;
	
	Bus(String _routeName, String _timeTillDepart) {
		routeName = _routeName;
		timeTillDepart = _timeTillDepart;
	}
	
	public Bus() {
	}
	
	void setRouteName (String _routeName) {
		routeName = _routeName;
	}
	
	void setTimeTill (String _timeTillDepart) {
		timeTillDepart = _timeTillDepart;
	}

	String getRouteName() {
		return routeName;
	}
	
	String getTimeTillDepart() {
		return timeTillDepart;
	}
	
	@Override public String toString() {
		return routeName + " " + timeTillDepart;		
	}
}