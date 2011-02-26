package com.islamsharabash.cumtd;


//Gets the search parameters and creates an object with raw data
//Has method setStop which sets the stop parameter
//Has method search which searches, has raw html. and then formats html
//Has method getResults which returns formatted search output

import java.net.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.content.Context;


public class CumtdSearch {
	//context to use resources, stop object to use
	final Context ctx;
	final Stop stop;
	
	CumtdSearch(Stop _stop, Context _ctx){
		stop = _stop;
		ctx = _ctx;
	}
	
	
	/**
	 * getTimes gets stop times and populates "bus" objects into object stop
	 * @return stop or null on failure
	 */
	public Stop getTimes() {
		try {
        /* Create a URL we want to load some xml-data from. */
		String sUrl = String.format(ctx.getResources().getString(R.string.searchURL), stop.getStopID());
        URL url = new URL(sUrl);

        /* Get a SAXParser from the SAXPArserFactory. */
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();

        /* Get the XMLReader of the SAXParser we created. */
        XMLReader xr = sp.getXMLReader();
        
        /* Create a new ContentHandler and apply it to the XML-Reader*/
        MtdXmlHandler mHandler = new MtdXmlHandler(stop);
        xr.setContentHandler(mHandler);
       
        /* Parse the xml-data from our URL. */
        xr.parse(new InputSource(url.openStream()));
        /* Parsing has finished. */
        
        return stop;
        
		} catch (Exception e) {}
		
		return null;
	}
	
}

