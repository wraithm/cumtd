package com.islamsharabash.cumtd;

import java.io.IOException;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;


public class FavoritesActivity extends ListActivity {
	
	Context ctx = FavoritesActivity.this;
	DataBaseHelper db = new DataBaseHelper(FavoritesActivity.this);
	StopAdapter mListAdapter = null;
	Cursor mCursor = null;
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
  	super.onCreate(savedInstanceState);
  	setContentView(R.layout.favorites);
    	
  	setupDB();
  	
	/** setup List/Cursor/Filter **/
	mCursor = db.getFavorites();

	mListAdapter = new StopAdapter(ctx, mCursor, db, cumtd.FAVORITESACTIVITY);
	setListAdapter(mListAdapter);
	
  }//onCreate close

  private void setupDB() {
	  try {
	  db.createDataBase();
	  } catch (IOException ioe) {
	  throw new Error("Unable to create database");
	  }

	  try {
	  db.openDataBase();
	  }catch(SQLException sqle){
	  throw sqle;
	  }
  }
  
  @Override
  public void onResume() {
	super.onResume();
	mCursor.requery();
	mListAdapter.notifyDataSetChanged();
  }
  
  @Override
  protected void onDestroy() {
   super.onDestroy();
   mCursor.close();
   db.close();
  }
 
}
