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

import com.devAlbaladejo.PokeCards.models.entities.Types;
import com.devAlbaladejo.PokeCards.models.services.ITypesService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class TypesRestController {

	@Autowired
	private ITypesService typesService;
	
	@GetMapping("/types")
	public List<Types> getTypes(){
		return typesService.findAll();
	}
	
	@GetMapping("types/{id}")
	public ResponseEntity<?> getType(@PathVariable Long id){
		Types type = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			type = typesService.findById(id);
		}catch (DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(type == null) {
			response.put("message", "Type ID (".concat(id.toString().concat(") doesn't exist in the database")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Types>(type, HttpStatus.OK);
	}
}
