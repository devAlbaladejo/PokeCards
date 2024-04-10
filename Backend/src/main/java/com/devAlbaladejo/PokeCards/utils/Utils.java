package com.devAlbaladejo.PokeCards.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	
	public static String encriptPasswword(String password) 
    {  
        MessageDigest md;
        
        StringBuilder hexString = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			 
	        BigInteger number = new BigInteger(1, md.digest(password.getBytes(StandardCharsets.UTF_8)));  
	  
	        hexString = new StringBuilder(number.toString(16));  
	  
	        while (hexString.length() < 32)  
	        {  
	            hexString.insert(0, '0');  
	        }  
		} catch (NoSuchAlgorithmException e) {
			hexString = null;
		}  
  
        return hexString.toString();
    }  
}
