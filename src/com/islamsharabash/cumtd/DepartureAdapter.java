package com.islamsharabash.cumtd;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.*;
import android.widget.*;


// similar to the ArrayAdapter, but doesn't need a text view
public class DepartureAdapter extends BaseAdapter implements ListAdapter {
	
	private List<Departure> departures = new ArrayList<Departure>();
	private LayoutInflater inflater;
	
	public DepartureAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
	}
	
	public void setDepartures(List<Departure> departures) {
		this.departures = departures;
	}

	@Override
	public int getCount() {
		return this.departures.size();
	}

	@Override
	public Object getItem(int position) {
		return this.departures.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// ref: http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/view/List14.html
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		// create the view or get references to view components
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.departure_list_item, null);	
			
            holder = new ViewHolder();
            holder.route = (TextView) convertView.findViewById(R.id.route);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            
            convertView.setTag(holder);
            
		} else {
	        holder = (ViewHolder) convertView.getTag();
		}
		
		// set data
		Departure departure = this.departures.get(position);
		holder.route.setText(departure.getRoute());
		holder.time.setText(departure.getTime());
		
		holder.time.setTextColor(departure.getTimeColor());
		
		return convertView;
	}
	
	static class ViewHolder {
	    TextView route;
	    TextView time;
	}
}
