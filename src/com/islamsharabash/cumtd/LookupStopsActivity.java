package com.islamsharabash.cumtd;

import java.io.IOException;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.*;
import android.database.SQLException;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;

public class LookupStopsActivity extends ListActivity {	
	
	Context ctx = LookupStopsActivity.this;
	DataBaseHelper db = new DataBaseHelper(LookupStopsActivity.this);
	private EditText filterText = null;
	public static boolean updateList = false;
	StopAdapter mListAdapter = null;
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
  	super.onCreate(savedInstanceState);
  	
  	setContentView(R.layout.lookupstops);
  	
  	setupDB();
  	
  	/** setup List/Cursor/Filter **/
	filterText = (EditText) findViewById(R.id.StopEditText);
	filterText.addTextChangedListener(filterTextWatcher);
	String searchText = filterText.getText().toString();
	mListAdapter = new StopAdapter(ctx, db.filter(searchText), db, cumtd.LOOKUPSTOPSACTIVITY);
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
	if (updateList) {
		String searchText = filterText.getText().toString();
		mListAdapter.changeCursor(db.filter(searchText));
		LookupStopsActivity.updateList = false;
	}
  }
  

  private TextWatcher filterTextWatcher = new TextWatcher() {

	@Override
	public void afterTextChanged(Editable s) {}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before,
			int count) {	
		mListAdapter.changeCursor(db.filter(s.toString()));
	}	
  };
	

  
  @Override
  protected void onDestroy() {
     super.onDestroy();
     filterText.removeTextChangedListener(filterTextWatcher);
     db.close();
    }
}

