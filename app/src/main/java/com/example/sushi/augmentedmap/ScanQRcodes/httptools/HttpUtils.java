package com.example.sushi.augmentedmap.ScanQRcodes.httptools;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

public class HttpUtils {

	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String getJsonContent(String url_path) {
		try {
			URL url = new URL(url_path);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(3000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			int code = connection.getResponseCode();
			if (code == 200) {
				return changeInputStream(connection.getInputStream());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	private static String changeInputStream(InputStream inputStream) {
		// TODO Auto-generated method stub
		String jsonString = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			jsonString = new String(outputStream.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}
	
	public static String getTransitTime(String jsonString) {
		String length = "null";
		try {
			JSONObject jObject = new JSONObject(jsonString);
			JSONArray jArray = jObject.getJSONArray("routes");
			JSONObject route = jArray.getJSONObject(0);
			JSONArray legs = route.getJSONArray("legs");
			JSONObject leg = legs.getJSONObject(0);
			JSONObject duration = leg.getJSONObject("duration");
			length = duration.getString("text");
			// String oneObjectsItem2 =
			// oneObject.getString("anotherSTRINGNAMEINtheARRAY");
		} catch (JSONException e) {
			length = "no";
		}

		return length;

	}
	
//	public static HashMap<String, String> getTransitionDetail(String jsonString){
		public static HashMap<String, String> getTransitionDetail(String jsonString){
			HashMap<String, String> detail = new HashMap<String, String>();
			try {
				JSONObject jObject = new JSONObject(jsonString);
				JSONArray jArray = jObject.getJSONArray("routes");
				JSONObject route = jArray.getJSONObject(0);
				JSONArray legs = route.getJSONArray("legs");
				JSONObject leg = legs.getJSONObject(0);
				JSONArray steps = leg.getJSONArray("steps");
				steps.getJSONObject(0);
				//				Log.i("scan","length"+step.length());
				// String oneObjectsItem2 =
				// oneObject.getString("anotherSTRINGNAMEINtheARRAY");
			} catch (JSONException e) {
				
			}
			
		
		
		
		
		
		
		
		
		return null;
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
