package com.devAlbaladejo.PokeCards.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devAlbaladejo.PokeCards.models.entities.Users;
import com.devAlbaladejo.PokeCards.models.services.IUsersService;
import com.devAlbaladejo.PokeCards.utils.Utils;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class UsersRestController {

	@Autowired
	private IUsersService usersService;
	
	@GetMapping("/users")
	public List<Users> getUsers(){
		return usersService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id){
		
		Users user = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			user = usersService.findById(id);
		}catch(DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(user == null) {
			response.put("message", "User ID(".concat(id.toString().concat(") doesn't exist in the database!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<Users>(user, HttpStatus.OK);
	}
	
	@PostMapping("/users/save")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createUser(@Valid @RequestBody Users user, BindingResult result) {
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			user.setPassword(Utils.encriptPasswword(user.getPassword()));
			user.setPoints(0);
			
			usersService.save(user);
		}catch(DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "User has been created!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}