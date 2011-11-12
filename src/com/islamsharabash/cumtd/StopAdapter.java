package com.islamsharabash.cumtd;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;


// similar to the ArrayAdapter, but doesn't need a text view
public class StopAdapter extends BaseAdapter implements ListAdapter {
	
	private List<Stop> stops = new ArrayList<Stop>();
	private LayoutInflater inflater;
	private Context context;
	
	public StopAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
	}
	
	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}

	@Override
	public int getCount() {
		return this.stops.size();
	}

	@Override
	public Object getItem(int position) {
		return this.stops.get(position);
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
			convertView = inflater.inflate(R.layout.stop_list_item, null);	
			
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.star = (CheckBox) convertView.findViewById(R.id.star);
            
            // set click listeners
            convertView.setOnClickListener(itemListener);
            holder.star.setOnClickListener(favoriteListener);
            
            convertView.setTag(holder);
		} else {
			Log.d("RECYCLE", "recycling view");
	        holder = (ViewHolder) convertView.getTag();
		}
		
		// set data
		Stop stop = this.stops.get(position);
		holder.text.setText(stop.getName());
		holder.star.setChecked(stop.isFavorite());
		holder.position = position;
		
		return convertView;
	}
	
	static class ViewHolder {
	    TextView text;
	    CheckBox star;
	    int position;
	}
	
	private OnClickListener itemListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
	        ViewHolder holder = (ViewHolder) view.getTag();
			Stop stop = stops.get(holder.position);
			DeparturesActivity.launchForStop(stop, context);
		}
	};
	
	private OnClickListener favoriteListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
	        ViewHolder holder = (ViewHolder) view.getTag();
			Stop stop = stops.get(holder.position);
			
			stop.toggleFavorite();
			holder.star.setChecked(stop.isFavorite());
		}
	};

	/**

	public View getView(int position, View convertView, ViewGroup parent) {
       
        holder.FavoriteCB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					db.setFavorite(holder.stop);
				} else {
					db.removeFavorite(holder.stop);
				}
				if (ACTIVITY == cumtd.FAVORITESACTIVITY) {
					getCursor().requery();
					notifyDataSetChanged();
					LookupStopsActivity.updateList = true;
				}
			}
        });
        
        convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Intent i = new Intent(v.getContext(), DisplaySearchResults.class);
		    	 Bundle stopBundle = new Bundle();
		         stopBundle.putSerializable("stop", holder.stop);
		         i.putExtra("com.islamsharabash.cumtd.stop", stopBundle);
		         v.getContext().startActivity(i);
			} 	
        });
        
        return convertView;
    }
    **/
}
