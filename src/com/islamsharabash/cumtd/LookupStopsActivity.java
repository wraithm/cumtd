package com.islamsharabash.cumtd;

import java.util.List;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;

public class LookupStopsActivity extends ListActivity {	
	
  DatabaseAPI db;
  StopAdapter adapter;
  private EditText filterET;
	
  @Override
  public void onCreate(Bundle savedInstanceState) {
  	super.onCreate(savedInstanceState);
  	setContentView(R.layout.lookupstops);
  	
	db = DatabaseAPI.getInstance();
	
  	adapter = new StopAdapter(this);
  	setListAdapter(adapter);
  	
	filterET = (EditText) findViewById(R.id.StopEditText);
	filterET.addTextChangedListener(filterTextWatcher);
  }
  
  @Override
  public void onResume() {
	super.onResume();
	String search = filterET.getText().toString();
	
	List<Stop> stops = db.getStops(search);
	adapter.setStops(stops);
	adapter.notifyDataSetChanged();
  }

  private TextWatcher filterTextWatcher = new TextWatcher() {

	@Override
	public void afterTextChanged(Editable s) {}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	
	@Override
	public void onTextChanged(CharSequence search, int start, int before, int count) {	
		List<Stop> stops = db.getStops(search.toString());
		adapter.setStops(stops);
		adapter.notifyDataSetChanged();
	}	
  };
  
  @Override
  protected void onDestroy() {
     super.onDestroy();
     filterET.removeTextChangedListener(filterTextWatcher);
  }
}

