package com.devAlbaladejo.PokeCards.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.devAlbaladejo.PokeCards.models.entities.Cards;
import com.devAlbaladejo.PokeCards.models.entities.Users;

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
        return new BCryptPasswordEncoder().encode(password);
    }  
	
	public static double convertIntToDouble(int number) {
		String numeroFlotante = String.valueOf(number);
		int longitud = numeroFlotante.length();
		String resultado = numeroFlotante.substring(0, longitud - 1) + "." + numeroFlotante.substring(longitud - 1);
		return Double.parseDouble(resultado);
	}
	
	public static boolean userHasPoints(Users user, int boxID) {
		
		switch (boxID) {
			case 1: {
				if(user.getPoints() < 100)
					return false;
				
				break;
			}
			case 2: {
				if(user.getPoints() < 200)
					return false;
				
				break;
			}
			case 3: {
				if(user.getPoints() < 300)
					return false;
				
				break;
			}
		}
		
		return true;
	}
	
	public static Cards getRandomCard(Integer[] probabilities, List<Cards> cards) {
		int random = randomNumber(100);
		List<Cards> filteredCards = null;
		int cardID;
		
		if(random <= probabilities[0]) {
			filteredCards = cards.stream().filter(e -> e.getRarities().getId() == 1).toList();
		}else if((random > probabilities[0]) && (random <= probabilities[1])) {
			filteredCards = cards.stream().filter(e -> e.getRarities().getId() == 2).toList();
		}else if((random > probabilities[1]) && (random <= probabilities[2])) {
			filteredCards = cards.stream().filter(e -> e.getRarities().getId() == 3).toList();
		}else if((random > probabilities[2]) && (random <= probabilities[3])) {
			filteredCards = cards.stream().filter(e -> e.getRarities().getId() == 4).toList();
		}else if((random > probabilities[3]) && (random <= probabilities[4])) {
			filteredCards = cards.stream().filter(e -> e.getRarities().getId() == 5).toList();
		}
		
		cardID = randomNumber(filteredCards.size() - 1);
		
		return filteredCards.get(cardID);
	}
	
	private static int randomNumber(int max) {
		Random rand = new Random();
		return rand.nextInt(max - 1 + 1) + 1;
	}
}
