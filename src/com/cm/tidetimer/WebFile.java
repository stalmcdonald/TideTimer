package com.cm.tidetimer;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WebFile {
	//build functionality to connect to a website and pull data back and test for internet connection


		static Boolean _conn = false; //always assume there's no connection
		static String _connectionType = "Unavailable";
		
		public static String getConnnectionType(Context context){ //runs function
			netInfo(context);
			return _connectionType;
		}
		
		public static Boolean getConnectionStatus(Context context){ //returns boolean
			netInfo(context);
			return _conn; //returns connection status
		}
		
		//test Internet connection on android device
		private static void netInfo(Context context){
			//creating 2 values representing if connected and what type of connection
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();//gets dynamic network info.
			if(ni != null){
				if(ni.isConnected()){
					_connectionType = ni.getTypeName();//if theres a connection and what type it is
					_conn = true;
			}
		
		}
	}	
		/*
		 * Working URL responder
		 */
		public static String getURLSTringResponse(URL url){
			String response = "";
			
			//run contact int specific
			//universal function
			//try catch statement
			try{
				URLConnection conn = url.openConnection();
				//accept the info url returns thru buffer input string
				BufferedInputStream bin = new BufferedInputStream(conn.getInputStream());
				
				byte[] contentBytes = new byte[1024];
				//loop thru bytes needed to get and keep track of them
				int bytesRead = 0;
				//holds data as it comes in as a string form.
				StringBuffer responseBuffer = new StringBuffer();
				
				//allow the bytes read to count up
				while((bytesRead = bin.read(contentBytes))!= -1){
					response = new String(contentBytes,0,bytesRead);
					//content buffered into a single string
					responseBuffer.append(response);
					
				}
				//response buffer holds all data
				return responseBuffer.toString();
			} catch (Exception e){
				Log.e("URL RESPONSE ERROR", "getURLStringRsponse");
			}
				
			
			return response;
		}
	}
