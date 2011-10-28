package com.islamsharabash.cumtd;

import java.util.List;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;


public class FavoritesActivity extends ListActivity {
	
	Context context = FavoritesActivity.this;
	DatabaseAPI db;
	StopAdapter adapter;
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
  	super.onCreate(savedInstanceState);
  	setContentView(R.layout.favorites);
  	
	db = DatabaseAPI.getInstance();
  	
	adapter = new StopAdapter(context);
	setListAdapter(adapter);
	
  }
  
  @Override
  public void onResume() {
	super.onResume();
	List<Stop> favorites = db.getFavoriteStops();
	adapter.setStops(favorites);
	adapter.notifyDataSetChanged();
	//TODO(ibash) might have to call refreshDrawableState on the listview... test it
  }
  
  @Override
  protected void onDestroy() {
   super.onDestroy();
  }
 
}
