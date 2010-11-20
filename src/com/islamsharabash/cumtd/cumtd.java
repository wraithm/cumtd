package com.islamsharabash.cumtd;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.islamsharabash.cumtd.CumtdDB;

/**
 * Opening Activity
 * Opens the database through helper
 * Implements UI backend
 * Performs filters/saves
 * Closes DB before search or on exit
 * Performs search
 * 
**/


public class cumtd extends ListActivity implements FilterQueryProvider{
	Cursor bookmarkCursor;
	Cursor filterCursor;
	SimpleCursorAdapter bookmarkAdapter;
	SimpleCursorAdapter filterAdapter;
	DataBaseHelper db = new DataBaseHelper(cumtd.this);
	Toast Toasty;
	//bundle to use for calls to display results
	Bundle stopBundle = new Bundle();
	final int DELETE_ID = 1;
	
	  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
  	super.onCreate(savedInstanceState);
  	
    	
  	/** setup the database **/
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
	
	

  	
  	/** Filter/Saving backend **/
  		  		
  		//bookmark backend
  		bookmarkCursor = db.getBookmarks();
  		startManagingCursor(bookmarkCursor);
  		bookmarkAdapter = new SimpleCursorAdapter(cumtd.this,
  								android.R.layout.simple_expandable_list_item_2,
  								bookmarkCursor,
  								new String[] {CumtdDB.LOCATION},
  								new int[] {android.R.id.text1});
  		setListAdapter(bookmarkAdapter);
  		
  		
  		//search backend
  		this.runQuery("");
  		filterAdapter = new SimpleCursorAdapter(cumtd.this,
  								android.R.layout.select_dialog_item,
  								filterCursor,
  								new String[] {CumtdDB.LOCATION},
  								new int[] {android.R.id.text1});
  		filterAdapter.setStringConversionColumn(2);	
  		filterAdapter.setFilterQueryProvider(cumtd.this);

  	/** setup the UI **/ 		
  		setContentView(R.layout.main);   	
  		final EditText edittext = (EditText) findViewById(R.id.StopEditText);
  		
  		AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.StopEditText);
      	acTextView.setThreshold(0);
      	acTextView.setAdapter(filterAdapter);
      	
     	//register the listview for a context menu
  		registerForContextMenu(getListView());
  		
  		

  	/** Implement search/save button **/
      //search button implementation	
  		final Button searchButton = (Button) findViewById(R.id.SearchButton01);
  		searchButton.setOnClickListener(new View.OnClickListener() {
  			public void onClick(View v) {
  			//get the stop id from the box from the user, format it and create stop object
  				try {
						Stop stop = formatQuery(edittext.getText().toString());
						
	                    Intent i = new Intent(cumtd.this, DisplayResults.class);
	                    stopBundle.putSerializable("stop", stop);
						i.putExtra("com.islamsharabash.cumtd.stop", stopBundle);
	                    startActivity(i);
						
					} catch (MalformedStop e) {
						Toasty = Toast.makeText(cumtd.this, (CharSequence) "Invalid Stop ID", 500);
						Toastme();
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	                    			                  			
          }
  		});
  	       	 	
  	  //save button implementation
  		final Button saveButton = (Button) findViewById(R.id.SaveButton01);
  		saveButton.setOnClickListener(new View.OnClickListener() {
  			public void onClick(View v) {
  				//save a bookmark
					Stop stop;
					try {
						//saveBookmark throws a malformed stop id as well if it can't find it
						stop = formatQuery(edittext.getText().toString());
						db.saveBookmark(stop);	

						//done saving
						bookmarkCursor.requery();
  						bookmarkAdapter.notifyDataSetChanged();
  						Toasty = Toast.makeText(cumtd.this, (CharSequence) "Stop Saved", 500);
  						Toastme();							
							
						} catch (MalformedStop e) {
							Toasty = Toast.makeText(cumtd.this, (CharSequence) "Invalid Stop ID", 500);
							Toastme();
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
      	}
  		});

  		
 
  }//onCreate close
		
	
	//sets up the toast postition etc
  private void Toastme(){
	Toasty.setGravity(48, 0, 50);
	Toasty.show();
  }	


  
  //autocomplete function		
	@Override
	public Cursor runQuery(CharSequence constraint) {
		String partialSearch = constraint.toString().trim();
		if (partialSearch.compareTo("") == 0)
			filterCursor = db.getAllEntries();
			else
			filterCursor = db.filter(partialSearch);
			startManagingCursor(filterCursor);
	        return filterCursor;
		}
	  	
    //override onCreateContextMenu to make the context menu for the listview
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
    	super.onCreateContextMenu(menu, v, menuInfo);
    	menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }
    
    //context menu for stops... i.e. delete
    public boolean onContextItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case DELETE_ID:
    		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    		db.removeBookmark(info.id);
    		bookmarkCursor.requery();
			bookmarkAdapter.notifyDataSetChanged();
			Toasty = Toast.makeText(cumtd.this, (CharSequence) "Deleted", 500);
			Toastme();    		
    		return true;
    	}
    	return super.onContextItemSelected(item);
    }

    //callback for clicking on an item, fires off the intent and stuff
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id){
    	super.onListItemClick(l, v, position, id);
    	
    	 Intent i = new Intent(cumtd.this, DisplayResults.class);
     	 db.getStop(id);
         stopBundle.putSerializable("stop", db.getStop(id));
         i.putExtra("com.islamsharabash.cumtd.stop", stopBundle);
         startActivity(i);
    }
                
    
    //Format stop code, returns a stop object with the id set
	//if the stop id is invalid, it will return a -1
	private Stop formatQuery (String query) throws MalformedStop {
		Pattern p = Pattern.compile("\\d{4}?");
		Matcher m = p.matcher(query);
		if(m.find()){
			return new Stop().setStopID(Integer.parseInt(m.group()));
		}
		//if we haven't returned...
		throw new MalformedStop();		
	}
	
	protected void onResume(){
		db.openDataBase();
		super.onResume();
	}
    
    protected void onPause() { 
    	//close db
    	db.close();
    	super.onPause();
    }
    
}


