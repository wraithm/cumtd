package com.islamsharabash.cumtd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

import android.content.res.Resources;
import android.util.Log;

// wrapper around cumtd's api
// http://developer.cumtd.com/
public class CumtdAPI {
	Resources res = CumtdApplication.getAppContext().getResources();
	
	public List<Departure> getDeparturesByStop(Stop stop) throws Exception {
		String method = "GetDeparturesByStop";
		String params = "?stop_id=" + stop.getID() + "&key=" + res.getString(R.string.API_KEY);
		String url = res.getString(R.string.API_URL) + method + params;
		
		JSONObject json_response = apiRequest(url);
		
		return JSONToDepartureList(json_response);
	}
	
	private List<Departure> JSONToDepartureList(JSONObject json) throws JSONException {
		List<Departure> departures =  new ArrayList<Departure>();
		JSONArray json_departures = json.getJSONArray("departures");
		for (int i = 0; i < json_departures.length(); i ++) {
			JSONObject departure = json_departures.getJSONObject(i);
			departures.add(new Departure(
				departure.getString("headsign"),
				departure.getInt("expected_mins"),
				departure.getJSONObject("route").getString("route_color")
			));
		}
		return departures;
	}
	
	// we're going to throw the IOException all the way to the ui...
	private JSONObject apiRequest(String url) throws IOException {
		Log.d("Making request to:", url);
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
