package com.devAlbaladejo.PokeCards.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devAlbaladejo.PokeCards.models.entities.Cards;
import com.devAlbaladejo.PokeCards.models.entities.Rarities;
import com.devAlbaladejo.PokeCards.models.entities.Types;
import com.devAlbaladejo.PokeCards.models.services.ICardsService;
import com.devAlbaladejo.PokeCards.models.services.IRaritiesService;
import com.devAlbaladejo.PokeCards.models.services.ITypesService;
import com.devAlbaladejo.PokeCards.utils.Utils;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class CardsRestController {

	@Autowired
	private ICardsService cardsService;
	
	@Autowired
	private ITypesService typesService;
	
	@Autowired
	private IRaritiesService raritiesService;
	
	@GetMapping("/cards")
	public List<Cards> getCards(){
		return cardsService.findAll();
	}
	
	@GetMapping("/cards/{id}")
	public ResponseEntity<?> getCard(@PathVariable Long id){
		Cards card = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			card = cardsService.findById(id);
		}catch(DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(card == null) {
			response.put("message", "Card ID(".concat(id.toString().concat(") doesn't exist in the database!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<Cards>(card, HttpStatus.OK);
	}
	
	// Insert all pokemon from specified generation
	/*@PostMapping("/cards/save/{generationID}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createCards(@PathVariable Long generationID){
		
		Cards card = new Cards();
		Types type = null;
		List<Types> types = typesService.findAll();
		List<Rarities> rarities = raritiesService.findAll();
		Map<String, Object> response = new HashMap<>();
		
		JSONObject jsonGeneration = Utils.readURL("https://pokeapi.co/api/v2/generation/" + generationID);
		JSONArray arrayPokemons = jsonGeneration.getJSONArray("pokemon_species");
		
		
		for(int i = 0; i < arrayPokemons.length(); i++) {
			JSONObject jsonPokemon = Utils.readURL("https://pokeapi.co/api/v2/pokemon/" + arrayPokemons.getJSONObject(i).getString("name"));
			System.out.println(jsonPokemon.getString("name"));
			card.setId(jsonPokemon.getInt("id"));
			card.setName(jsonPokemon.getString("name"));
			card.setImage(jsonPokemon.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default"));
			
			JSONArray arrayPokemonType = jsonPokemon.getJSONArray("types");
			type = types.stream().filter(e -> e.getName().toLowerCase().equals(arrayPokemonType.getJSONObject(0).getJSONObject("type").getString("name").toLowerCase())).findAny().orElse(null);
			card.setPrimaryType(type);
			
			if(arrayPokemonType.length() == 2) {
				type = types.stream().filter(e -> e.getName().toLowerCase().equals(arrayPokemonType.getJSONObject(1).getJSONObject("type").getString("name").toLowerCase())).findAny().orElse(null);
				card.setSecondaryType(type);
			}else {
				card.setSecondaryType(null);
			}
			
			card.setHeight(jsonPokemon.getInt("height"));
			card.setWeight(jsonPokemon.getInt("weight"));
			
			JSONArray arrayStats = jsonPokemon.getJSONArray("stats");
			card.setHp(arrayStats.getJSONObject(0).getInt("base_stat"));
			card.setAttack(arrayStats.getJSONObject(1).getInt("base_stat"));
			card.setDefense(arrayStats.getJSONObject(2).getInt("base_stat"));
			card.setSpecialAttack(arrayStats.getJSONObject(3).getInt("base_stat"));
			card.setSpecialDefense(arrayStats.getJSONObject(4).getInt("base_stat"));
			card.setSpeed(arrayStats.getJSONObject(5).getInt("base_stat"));
			
			card.setRarities(rarities.get(0));
			
			cardsService.save(card);
		}
		
		response.put("message", "All pokemons from generation " + generationID + " have been inserted successfully");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	}*/
}
