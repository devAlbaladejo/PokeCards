package com.devAlbaladejo.PokeCards.controllers;

import java.lang.reflect.Array;
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

import com.devAlbaladejo.PokeCards.models.entities.Cards;
import com.devAlbaladejo.PokeCards.models.entities.Usercards;
import com.devAlbaladejo.PokeCards.models.entities.Users;
import com.devAlbaladejo.PokeCards.models.services.ICardsService;
import com.devAlbaladejo.PokeCards.models.services.IUserCardsService;
import com.devAlbaladejo.PokeCards.models.services.IUsersService;
import com.devAlbaladejo.PokeCards.utils.Utils;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class UserCardsRestController {

	@Autowired
	private IUserCardsService userCardsService;
	
	@Autowired
	private IUsersService usersService;
	
	@Autowired
	private ICardsService cardsService;
	
	@GetMapping("/userCards/{id}")
	public List<Usercards> getUserCards(@PathVariable Long id){
		List<Usercards> allUserCards = userCardsService.findAll();
		
		List<Usercards> userCards = allUserCards.stream().filter(e -> e.getUsers().getId() == id).collect(Collectors.toList());
		
		return userCards;
	}
	
	@PostMapping("/userCards/save/{giftID}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createUserCard(@PathVariable Long giftID, @Valid @RequestBody Users user, BindingResult result) {

		Map<String, Object> response = new HashMap<>();
		Map<Integer, Integer[]> probabilities = new HashMap<>();
		probabilities.put(1, new Integer[] {44,74,92,99,100});
		probabilities.put(2, new Integer[] {30,62,85,97,100});
		probabilities.put(3, new Integer[] {17,55,80,95,100});
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {		
			List<Cards> cards =  cardsService.findAll();
			if(Utils.userHasPoints(user,giftID.intValue())) {
				Cards card = Utils.getRandomCard(probabilities.get(giftID.intValue()), cards);
				Usercards usercards = userCardsService.findByUserIdAndCardId(user.getId(), card.getId());
				
				if(usercards == null) {
					usercards = new Usercards();
					usercards.setUsers(user);
					usercards.setCards(card);
					usercards.setAmount(1);
				}
				else
					usercards.setAmount(usercards.getAmount() + 1);
					
				userCardsService.save(usercards);
				
				int points = 0;
				
				if(giftID == 1)
					points = 100;
				else if(giftID == 2)
					points = 200;
				else if(giftID == 3)
					points = 300;
				
				user.setPoints((user.getPoints()) - points);
				
				usersService.save(user);
				response.put("users", usercards.getUsers());
				response.put("cards", usercards.getCards());
				response.put("amount", usercards.getAmount());
			}
			else {
				response.put("message", "Error opening gift");
				response.put("error", "Insufficent points");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
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

