/*
 * 
 * Crystal McDonald
 * Java II
 * 1308
 * Week 1
 * 
 */package com.cm.tidetimer;

import java.util.ArrayList;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class LocDisplay extends LinearLayout{
	//two buttons and dropdown menu
	Button _add; //adds popular location
	Button _remove;//deletes
	Spinner _list; //drop down menu
	Context _context;
	//populating spinner with values
	//Instantiate as a new array list
	ArrayList<String> _faveWater = new ArrayList<String>();
	
	public LocDisplay(Context context){
		super(context);
		_context = context;
		
		//add a layout param
		LayoutParams lp;
		
		//adding default value(set hint)
		_faveWater.add("Select Favorites");
		
		//Display will hold 2 buttons (add locations to list/remove locations)  and a dropdown menu(spinner)
		_list = new Spinner(_context);
		lp = new LayoutParams(0,LayoutParams.WRAP_CONTENT, 1.0f);
		_list.setLayoutParams(lp);
		
		//defining a new array adapter using to populate the drop down list
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_spinner_item, _faveWater);
		//need a view resource for drop down
		listAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		//connecting list adapter to list
		_list.setAdapter(listAdapter);;
		_list.setOnItemSelectedListener(new OnItemSelectedListener(){
			
			//execute when select something
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos, long id){//takes a view for what was clicked
				Log.i("LOCATION SELECTED", parent.getItemAtPosition(pos).toString());
			}
			
			//execute when nothing is selected
			@Override
			public void onNothingSelected(AdapterView<?> parent){ //only selects adapter view so I know what triggers it
				Log.i("LOCATION SELECTED", "NONE");
			}

		});
			
		updateFavorites();
		
		_add = new Button(_context);
		_add.setText("Add");
		
		_remove = new Button(_context);
		_remove.setText("Delete");
		
		this.addView(_list);
		this.addView(_add);
		this.addView(_remove);
		
		
		//location display will be full width allows spinner to take up the space of screen
		lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(lp);
		
		
		}
	
	//creating a private function
	private void updateFavorites(){
		_faveWater.add("SEATTLE");
		_faveWater.add("SAN FRANCISCO");
		_faveWater.add("NEWPORT BAY");
		_faveWater.add("GULF OF MEXICO");
		_faveWater.add("GULF OF ST LAWRENCE");
		_faveWater.add("HUDSON BAY");
		_faveWater.add("GULF OF ALASKA");
		_faveWater.add("BAFFIN BAY");
		_faveWater.add("LAKE ERIE");
	}
}