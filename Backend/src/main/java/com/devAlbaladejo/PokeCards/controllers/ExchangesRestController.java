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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devAlbaladejo.PokeCards.models.entities.ExchangeOffers;
import com.devAlbaladejo.PokeCards.models.entities.Exchanges;
import com.devAlbaladejo.PokeCards.models.entities.Usercards;
import com.devAlbaladejo.PokeCards.models.services.IExchangeOffersService;
import com.devAlbaladejo.PokeCards.models.services.IExchangeService;
import com.devAlbaladejo.PokeCards.models.services.IUserCardsService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class ExchangesRestController {

	@Autowired
	private IExchangeService exchangeService;
	
	@Autowired
	private IUserCardsService userCardsService;
	
	@Autowired
	private IExchangeOffersService exchangeOffersService;
	
	@GetMapping("/exchanges")
	public List<Exchanges> getExchanges(){
		return exchangeService.findAll();
	}
	
	@PostMapping("/exchanges/change")
	public ResponseEntity<?> createExchange(@Valid @RequestBody Exchanges exchanges, BindingResult result){
		
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
			
			if(exchanges.getExchangeOffer().getActive() == 0) {
				response.put("message", "Error having exchange");
				response.put("error", "Exchange offer not available");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			Usercards usercardsOffer = userCardsService.findByUserIdAndCardId(exchanges.getExchangeOffer().getUserOffer().getId(), exchanges.getCardDemand().getId());
			Usercards usercardsDemand = userCardsService.findByUserIdAndCardId(exchanges.getUserDemand().getId(), exchanges.getCardDemand().getId());
			
			
			if(usercardsDemand == null) {
				response.put("message", "Error having exchange");
				response.put("error", "You don't have this demand card");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			else {
				// User Demand
				usercardsDemand.setAmount((usercardsDemand.getAmount() - 1));
				
				if(usercardsDemand.getAmount() == 0)
					userCardsService.delete(usercardsDemand);
				else
					userCardsService.save(usercardsDemand);
				
				usercardsDemand = userCardsService.findByUserIdAndCardId(exchanges.getUserDemand().getId(), exchanges.getExchangeOffer().getCardOffer().getId());
				
				if(usercardsDemand == null) {
					usercardsDemand = new Usercards();
					usercardsDemand.setUsers(exchanges.getUserDemand());
					usercardsDemand.setCards(exchanges.getExchangeOffer().getCardOffer());
					usercardsDemand.setAmount(1);
				}
				else {
					usercardsDemand.setAmount(usercardsDemand.getAmount() + 1);
				}
				
				userCardsService.save(usercardsDemand);
				
				// User Offer
				if(usercardsOffer == null) {
					usercardsOffer = new Usercards();
					usercardsOffer.setUsers(exchanges.getExchangeOffer().getUserOffer());
					usercardsOffer.setCards(exchanges.getCardDemand());
					usercardsOffer.setAmount(1);
				}
				else {
					usercardsOffer.setAmount(usercardsOffer.getAmount() + 1);
				}
				
				userCardsService.save(usercardsOffer);
				
				exchangeService.save(exchanges);
				
				ExchangeOffers exchangeOffers = exchanges.getExchangeOffer();
				exchangeOffers.setActive(0);
				exchangeOffersService.save(exchangeOffers);
			}
			
		}catch(DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		System.out.println(2);
		response.put("message", "Exchange has been made!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
