package com.islamsharabash.cumtd;


//Gets the search parameters and creates an object with raw data
//Has method setStop which sets the stop parameter
//Has method search which searches, has raw html. and then formats html
//Has method getResults which returns formatted search output

import java.net.*;
import java.io.*;
import java.util.regex.*;

import android.content.Context;


public class CumtdSearch {
	//context to use resources, stop object to use
	final Context ctx;
	final Stop stop;
	CumtdSearch(Stop _stop, Context _ctx){
		stop = _stop;
		ctx = _ctx;
	}
	
	//string to store htmlText
	private String htmlText = "";
	//String[] to store results, title is @0, body text @1
 	private String[] results = new String[2];

	
 	public CumtdSearch fetch() throws IOException{
 		connect();
 		format();
 		return this;
 	}
 	
	//takes a stop id (as integer) and returns the html as string
	private void connect() throws IOException {	
			//Create a HttpURLConnection and GET the HTML page
			URL url = new URL(String.format(ctx.getResources().getString(R.string.searchURL), stop.getStopID()));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", ctx.getResources().getString(R.string.userAgent));
	    
		//Create a stream to get the response
			BufferedReader stream = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    
	    //Create a temporary string to hold each line
		//and then add it to all the text
			String tempString;
			while ((tempString = stream.readLine()) != null) {
				htmlText += tempString;
			}
			
		//close the stream
			stream.close();
	 }
		
	//formats the htmlText into a string array with title @0 and bodyText@1
	private void format(){
	
		/**Warning! Shitty fix below! Fix it RIGHT!**/
		
			                    	
		 //Find the line with the stop and extract it
			Pattern p = Pattern.compile(ctx.getResources().getString(R.string.regex1));
			Matcher m = p.matcher(htmlText);
			m.find();
			//Check if a stop was even found
			if (m.group().substring(46, m.group().indexOf('<', 46)).equals("STOP LOOKUP")){
				results[0] = "Stop not found!";
				results[1] = "";
			} else{
			results[0] = m.group();
			results[0] = results[0].substring(46, results[0].indexOf('[', 46));
					
		//Find all matches and add them to display
			 p = Pattern.compile(ctx.getResources().getString(R.string.regex2));
			 m = p.matcher(htmlText);

		//clean body text from null
			 results[1] ="";
			 String temp;
			 while(m.find()){
					temp = m.group();
					temp = temp.substring(58, temp.indexOf('<', 58));
					results[1] += "\n" + temp;
		 
			 }
			}
	 }
	
	//return results	
	public String[] getResults(){
		return results;
	}
	
	//return a stop object with Data
	public Stop getStop(){
		return stop.setName(results[0]);
	}
	
	
}

