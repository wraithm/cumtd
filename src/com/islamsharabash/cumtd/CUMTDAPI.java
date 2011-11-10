package com.islamsharabash.cumtd;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

import android.content.res.Resources;

// wrapper around cumtd's api
// http://developer.cumtd.com/
public class CumtdAPI {
	Resources res = CumtdApplication.getAppContext().getResources();
	
	public String getDeparturesByStop(Stop stop) throws IOException {
		String method = "departures.getListByStop";
		String params = "?stop_id=" + stop.getID() + "&key=" + res.getString(R.string.API_KEY);
		String url = res.getString(R.string.API_URL) + method + params;
		
		JSONObject json_response = apiRequest(url);
		
		return formatDepartures(json_response);
	}
	
	private String formatDepartures(JSONObject json) {
		try {
			//TODO(ibash) make this return a well formed array list
			String response = "";
			JSONArray departures = json.getJSONArray("departures");
			for (int i = 0; i < departures.length(); i ++) {
				JSONObject departure = departures.getJSONObject(i);
				response += departure.getString("route");
				response += ": " + departure.getString("expected");
				response += "\n";
			}
			return response;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "Connection Error";
	}
	
	// we're going to throw the IOException all the way to the ui...
	private JSONObject apiRequest(String url) throws IOException {
		// perform http request
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String json = "{}";
		try {
			json = httpclient.execute(httpget, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			return (JSONObject) new JSONTokener(json).nextValue();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
