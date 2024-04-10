package com.devAlbaladejo.PokeCards.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class Utils {

	public static JSONObject readURL(String urlString) {
		
		JSONObject json = null;
		
		try {
			
			URL url = new URL(urlString);
			
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			StringBuilder stringBuilder = new StringBuilder();
			
			String line;
			
			while((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
			
			reader.close();
			
			json = new JSONObject(stringBuilder.toString());
			
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
		return json;
	}
}
