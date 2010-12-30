package com.islamsharabash.cumtd;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class StopAdapter extends ResourceCursorAdapter {

	private LayoutInflater mInflater;
	DataBaseHelper db;
	Context ctx;
	int ACTIVITY;

	public StopAdapter(Context context, Cursor c, DataBaseHelper _db, int _activity) {
		super(context, R.layout.stop_list_item, c);
		mInflater = LayoutInflater.from(context);
		db = _db;
		ACTIVITY = _activity;
	}
	
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return li.inflate(R.layout.stop_list_item, parent, false);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView NameTV = (TextView) view.findViewById(R.id.NameTV);
		CheckBox FavoriteCB = (CheckBox) view.findViewById(R.id.FavoriteCB);
		
		NameTV.setText(cursor.getString(1));
		FavoriteCB.setChecked((cursor.getInt(4) == 1));
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
        // A ViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
        final ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.stop_list_item, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.NameTV = (TextView) convertView.findViewById(R.id.NameTV);
            holder.FavoriteCB = (CheckBox) convertView.findViewById(R.id.FavoriteCB);
            holder.stop = new Stop(0000, "undefined name", 0, 0, false);

            convertView.setTag(holder);
            
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        // using minimal stops
        
        // Bind the data efficiently with the holder.
        Cursor mCursor = (Cursor) getItem(position);
        holder.stop = new Stop(mCursor.getInt(0), mCursor.getString(1));;
        holder.NameTV.setText(holder.stop.getName());
        holder.FavoriteCB.setChecked((mCursor.getInt(4) == 1));
        
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

	
    static class ViewHolder {
        TextView NameTV;
        CheckBox FavoriteCB;
        Stop stop;
    }

}
