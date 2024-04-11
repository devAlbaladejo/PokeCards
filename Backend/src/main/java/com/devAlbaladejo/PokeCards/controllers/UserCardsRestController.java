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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devAlbaladejo.PokeCards.models.entities.Usercards;
import com.devAlbaladejo.PokeCards.models.services.IUserCardsService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class UserCardsRestController {

	@Autowired
	private IUserCardsService userCardsService;
	
	@GetMapping("/userCards/{id}")
	public List<Usercards> getUserCards(@PathVariable Long id){
		List<Usercards> allUserCards = userCardsService.findAll();
		
		List<Usercards> userCards = allUserCards.stream().filter(e -> e.getUsers().getId() == id).collect(Collectors.toList());
		
		return userCards;
	}
	
	@PostMapping("/userCards/save")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createUserCard(@Valid @RequestBody Usercards userCard, BindingResult result) {

		Usercards usercards = userCardsService.findByUserIdAndCardId(userCard.getUsers().getId(), userCard.getCards().getId());
		
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
			
			if(usercards == null) {
				usercards = new Usercards();
				usercards.setUsers(userCard.getUsers());
				usercards.setCards(userCard.getCards());
				usercards.setAmount(1);
			}
			else
				usercards.setAmount(usercards.getAmount() + 1);
				
			userCardsService.save(usercards);
		}catch(DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Card has been added to User");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/userCards/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteUserCard(@Valid @RequestBody Usercards userCard) {
		Usercards usercards = userCardsService.findByUserIdAndCardId(userCard.getUsers().getId(), userCard.getCards().getId());
		
		Map<String, Object> response = new HashMap<>();
		
		if(usercards == null) {
			response.put("message", "User haven't got that card");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			usercards.setAmount((usercards.getAmount() - 1));
			
			if(usercards.getAmount() == 0)
				userCardsService.delete(usercards);
			else
				userCardsService.save(usercards);
			
		}catch(DataAccessException e) {
			response.put("message", "Error to delete the card to user");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Card has been deleted to User");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}

