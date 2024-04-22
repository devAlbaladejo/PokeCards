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
import org.springframework.web.bind.annotation.RestController;

import com.devAlbaladejo.PokeCards.models.entities.ExchangeOffers;
import com.devAlbaladejo.PokeCards.models.entities.Usercards;
import com.devAlbaladejo.PokeCards.models.services.IExchangeOffersService;
import com.devAlbaladejo.PokeCards.models.services.IUserCardsService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class ExchangeOffersRestController {

	@Autowired
	private IExchangeOffersService exchangeOffersService;
	
	@Autowired
	private IUserCardsService userCardsService;
	
	@GetMapping("/exchangeOffers")
	public List<ExchangeOffers> getExchangeOffers(){
		return exchangeOffersService.findAll();
	}
	
	@PostMapping("/exchangeOffers/save")
	public ResponseEntity<?> createExchangeOffer(@Valid @RequestBody ExchangeOffers exchangeOffers, BindingResult result){
		
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
			Usercards usercards = userCardsService.findByUserIdAndCardId(exchangeOffers.getUserOffer().getId(), exchangeOffers.getCardOffer().getId());
			
			usercards.setAmount((usercards.getAmount() - 1));
			
			if(usercards.getAmount() == 0)
				userCardsService.delete(usercards);
			else
				userCardsService.save(usercards);
			
			exchangeOffersService.save(exchangeOffers);
		}catch(DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Exchange offer has been created!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/exchangeOffers/delete/{id}")
	public ResponseEntity<?> deleteExchangeOffer(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		ExchangeOffers exchangeOffers = exchangeOffersService.findById(id);
		
		if(exchangeOffers == null) {
			response.put("message", "Exchange Offer don't exist.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			Usercards usercards = userCardsService.findByUserIdAndCardId(exchangeOffers.getUserOffer().getId(), exchangeOffers.getCardOffer().getId());
			
			if(usercards == null) {
				usercards = new Usercards();
				usercards.setUsers(exchangeOffers.getUserOffer());
				usercards.setCards(exchangeOffers.getCardOffer());
				usercards.setAmount(1);
			}
			else {
				usercards.setAmount(usercards.getAmount() + 1);
			}
			
			userCardsService.save(usercards);
			
			exchangeOffersService.deleteById(id);
			
		}catch(DataAccessException e) {
			response.put("message", "Error to delete the card to user");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Card has been deleted to User");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
