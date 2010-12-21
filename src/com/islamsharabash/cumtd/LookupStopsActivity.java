package com.islamsharabash.cumtd;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.*;
import android.database.SQLException;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import java.io.IOException;

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
    	
  	/** setup the database **/
	setupDB();
	
  	/** setup List/Cursor/Filter **/
	filterText = (EditText) findViewById(R.id.StopEditText);
	filterText.addTextChangedListener(filterTextWatcher);
	setListAdapter(new StopAdapter(ctx, db.filter(filterText.getText().toString()), db, cumtd.LOOKUPSTOPSACTIVITY));
  }//onCreate close
  
  @Override
  public void onResume() {
	super.onResume();
	if (updateList) {
		setListAdapter(new StopAdapter(ctx, db.filter(filterText.getText().toString()), db, cumtd.LOOKUPSTOPSACTIVITY));
		LookupStopsActivity.updateList = false;
	}
  }
  
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
	
  private TextWatcher filterTextWatcher = new TextWatcher() {

	@Override
	public void afterTextChanged(Editable s) {}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before,
			int count) {	
		setListAdapter(new StopAdapter(ctx, db.filter(s.toString()), db, cumtd.LOOKUPSTOPSACTIVITY));
	}	
  };
	              
  @Override
  protected void onDestroy() { 
    	super.onDestroy();
    	filterText.removeTextChangedListener(filterTextWatcher);
    	db.close();
    }
    
}

