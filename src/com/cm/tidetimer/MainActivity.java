/*
 * 
 * Crystal McDonald
 * Java II
 * 1308
 * Week 1
 * 
 */
package com.cm.tidetimer;

import com.cm.tidetimer.WebFile;
import com.cm.tidetimer.DataFile;
import com.cm.tidetimer.SearchField;
import com.cm.tidetimer.TideDisplay;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import android.util.Log;

import android.os.Bundle;
import android.os.AsyncTask;

import android.app.Activity;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	
	Context _context;
	LinearLayout _appLayout;
	SearchField _search; 
	TideDisplay _tidePrediction;
	LocDisplay _faveWater;
	Boolean _connected = false;//want to assume not connected
	HashMap<String, String> _history;
	TextView _tideSite;
	TextView _tzname;
	TextView _unitst;
	TextView _type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //LinearLayout
        _context = this;
        _appLayout = new LinearLayout(this);
        _history = getHistory();
        Log.i("HISTORY READ",_history.toString());
        
       TextView locLabel = new TextView(this);
        locLabel.setText("For tide prediction information enter proposed information.");
        _appLayout.addView(locLabel);

        
        //add it to view to see it
      		_search = new SearchField(_context, "Enter a City", "Go");
      		
      		//get buttons and fields
      		//add search handler
      		//building this way using more of a class method instead of tags approach
      		//EditText searchField = _search.getField();
      		Button searchButton = _search.getButton();
      		
      		//Adding functionality to get buttons to do something
      		searchButton.setOnClickListener(new OnClickListener() {
      			
      			@Override
      			public void onClick(View v){
      				Log.i("CITY ENTERED: ",_search.getField().getText().toString());
      				getfaveWatLoc(_search.getField().getText().toString());
      			}	
      		});
      		
      		
      		//Detects the network connection
      		_connected = WebFile.getConnectionStatus(_context);
      		if(_connected){
      			Log.i("NETWORK CONNECTION ", WebFile.getConnnectionType(_context));
      		}
      		
      		//add Prediction display
      		_tidePrediction = new TideDisplay(_context);
      		
      		//add faves display
      		_faveWater = new LocDisplay(_context);
      		
      		//add views to main layout
      		//added button to LinearLayout
      		_appLayout.addView(_search);
      		_appLayout.addView(_tidePrediction);
      		_appLayout.addView(_faveWater);
      		
      		
      		//to display under the search bar
      		_appLayout.setOrientation(LinearLayout.VERTICAL);
        
        setContentView(_appLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
   
    //JSON Path Example: http://api.wunderground.com/api/d4509f6df6f598a0/tide/q/Seattle.json

  //Create my custom API URL
    private void getfaveWatLoc(String city){
    	Log.i("CLICK",city);
    	//JSON output.  tide by city.
    	String baseURL = "http://api.wunderground.com/api/d4509f6df6f598a0/tide/q/WA/"+ city +".json";
    			
    	URL finalURL;
    	try{
    		finalURL = new URL(baseURL + city + ".json");
    		//finalURL = new URL(baseURL);
    		Log.i("my url:", baseURL);
    		LocRequest lr = new LocRequest();
    		lr.execute(finalURL);
    		
    	} catch (MalformedURLException e){
    		Log.e("BAD URL", "MALFORMED URL");
    		finalURL = null;
    	}
    }
    
    //create method to get history from Hard drive
    @SuppressWarnings("unchecked")
	private HashMap<String, String> getHistory(){
    	Object stored = DataFile.readObjectFile(_context, "history", false);
    	
    	HashMap<String, String> history;
    	if(stored == null){
    		Log.i("HISTORY", "NO HISTORY FILE FOUND");
    		history = new HashMap<String, String>();
    	}else{
    		history = (HashMap<String, String>)stored;
    	}
    	return history;
    }
    
    private class LocRequest extends AsyncTask<URL,Void,String>{
    	//override 2 separate functions
    	@Override
    	protected String doInBackground(URL...urls){
    		String response = "";
    		//pass an array even though it only holds one
    		for(URL url: urls){
    			response = WebFile.getURLSTringResponse(url);
    		}
    		return response;
    	}
    	
    	@Override
    	protected void onPostExecute(String result){
    		Log.d("URL RESPONSE",result);
    		//Still working on Parsing Data, decided to go back to AsyncTask and do without xml layout until I can work out the rest of the kinks one by one.  
    		//Running out of time hopefully I will be able to fix a few requirements by tonight.
    		//Receiving a JSON error JSON OBJECT EXCEPTION org.json.JSONException: End of input at character 0 of not really sure even after googling what this means
    		//my URL is accurate, I am not sure I am targeting data correctly.
    		try{
    		//parsing through JSON Data   accepts a string as a parameter
    		JSONObject json = new JSONObject(result);
    		JSONObject results = json.getJSONObject("tideInfo.tideSite");//.getJSONObject("tideSite");
    		if(results.getString("tideInfo").compareTo("type")==1){
//    			Toast toast = Toast.makeText(_context, "Invalid City Entered ", Toast.LENGTH_LONG);
//    			toast.show();
    		}else{
    			Toast toast = Toast.makeText(_context, "Tide Info" + results.get("tideInfo"), Toast.LENGTH_LONG);
    			toast.show();
    			
    			//makes sure history is there
    			_history.put(results.getString("string"), results.toString());
    			//target file to write history to harddrive
    			DataFile.storeObjectFile(_context, "tide", _history, false);
    			DataFile.storeStringFile(_context, "tide", results.toString(), true);
    		}
    		} 
    		catch (JSONException e){
    			Log.e("JSON", "JSON OBJECT EXCEPTION " + e.toString());
    		}
    	}
    	
    }

}
