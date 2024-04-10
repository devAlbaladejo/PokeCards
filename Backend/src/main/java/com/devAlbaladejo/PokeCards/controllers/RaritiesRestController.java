package com.devAlbaladejo.PokeCards.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devAlbaladejo.PokeCards.models.entities.Rarities;
import com.devAlbaladejo.PokeCards.models.services.IRaritiesService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class RaritiesRestController {

	@Autowired
	private IRaritiesService raritiesService;
	
	@GetMapping("/rarities")
	public List<Rarities> getRarities(){
		return raritiesService.findAll();
	}
	
	@GetMapping("/rarities/{id}")
	public ResponseEntity<?> getRarity(@PathVariable Long id){
		Rarities rarity = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			rarity = raritiesService.findById(id);
		}catch (DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(rarity == null) {
			response.put("message", "Rarity ID (".concat(id.toString().concat(") doesn't exist in the database")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Rarities>(rarity, HttpStatus.OK);
	}
}
