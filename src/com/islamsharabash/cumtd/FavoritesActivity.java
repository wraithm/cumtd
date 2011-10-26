package com.islamsharabash.cumtd;

import java.io.IOException;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;


public class FavoritesActivity extends ListActivity {
	
	Context context = FavoritesActivity.this;
	DatabaseAPI db = DatabaseAPI.getInstance();
	StopAdapter adapter = null;
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
  	super.onCreate(savedInstanceState);
  	setContentView(R.layout.favorites);
  	
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
