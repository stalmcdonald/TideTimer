package com.cm.tidetimer;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;

import com.cm.tidetimer.WebFile;
import com.cm.tidetimer.DataFile;


public class MainActivity extends Activity {
	
	Context _context;
	LinearLayout _appLayout;
	Boolean _connected = false;
	HashMap<String, String> _history;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //LinearLayout
        _context = this;
        

        Log.i("HISTORY READ",_history.toString());
	
	
	//DETECT NETWORK CONNECTION
	_connected = WebFile.getConnectionStatus(_context);
	if(_connected){
		Log.i("NETWORK CONNECTION",WebFile.getConnnectionType(_context));
	}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
