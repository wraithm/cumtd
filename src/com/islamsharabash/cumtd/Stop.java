package com.islamsharabash.cumtd;

import java.io.Serializable;


/**
 * @author Islam
 * Object to encapsulate Stop id and location
 * Can be extended to hold other information about the stop later
 */
public class Stop implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String stopLocation;
	private int stopID;
	
	public Stop(){
	}
	
	public Stop(int _stopID){
		stopID = _stopID;
	}
	
	public Stop(int _stopID, String _stopLocation){
		stopID = _stopID;
		stopLocation = _stopLocation;
	}
	
	public Stop setStopID(int _stopID){
		stopID = _stopID;
		return this;
	}
	
	public Stop setStopLocation(String _stopLocation){
		stopLocation = _stopLocation;
		return this;
	}
	
	public int getStopID(){
		return stopID;
	}
	
	public String getStopLocation(){
		return stopLocation;
	}

}
