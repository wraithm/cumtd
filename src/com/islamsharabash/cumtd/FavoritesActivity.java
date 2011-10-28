package com.islamsharabash.cumtd;

import java.util.List;
import android.app.ListActivity;
import android.os.Bundle;

public class FavoritesActivity extends ListActivity {
	
	DatabaseAPI db;
	StopAdapter adapter;
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
  	super.onCreate(savedInstanceState);
  	setContentView(R.layout.favorites);
  	
	db = DatabaseAPI.getInstance();
	adapter = new StopAdapter(this);
	setListAdapter(adapter);
	
  }
  
  @Override
  public void onResume() {
	super.onResume();
	List<Stop> favorites = db.getFavoriteStops();
	adapter.setStops(favorites);
	adapter.notifyDataSetChanged();
  }
  
  @Override
  protected void onDestroy() {
   super.onDestroy();
  }
 
}
