/*
 * 
 * Crystal McDonald
 * Java II
 * 1308
 * Week 1
 * 
 */

package com.cm.tidetimer;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class TideDisplay extends GridLayout{

	
	TextView _dateTime;//pretty
	TextView _loc;//tideSite
	TextView _height;// swell height
	TextView _type; //high or Low tide
	Context _context;
	
	
	public TideDisplay(Context context){
		super(context);
		
		//assigning a value
		_context = context;
		
		//creating columns/rows  1 for labels and 1 for values
		this.setColumnCount(2);
		
		TextView dateTimeLabel = new TextView(_context);
		dateTimeLabel.setText("Date & Time: ");
		_dateTime = new TextView(_context);
		
		TextView locLabel = new TextView(_context);
		locLabel.setText("Location: ");
		_loc = new TextView(_context);
		
		TextView heightLabel = new TextView(_context);
		heightLabel.setText("Swell: ");
		_height = new TextView(_context);
		
		TextView HorLLabel = new TextView(_context);
		HorLLabel.setText("Tidal Prediction: ");
		_type = new TextView(_context);
		
		
		//add views to display
		this.addView(dateTimeLabel);
		this.addView(_dateTime);
		this.addView(locLabel);
		this.addView(_loc);
		this.addView(heightLabel);
		this.addView(_height);
		this.addView(HorLLabel);
		this.addView(_type);
		
		
		
		
	}
}
