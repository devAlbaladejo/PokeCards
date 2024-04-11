package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import com.devAlbaladejo.PokeCards.models.entities.Usercards;

public interface IUserCardsService {

	public List<Usercards> findAll();
	
	public void save(Usercards userCard);
	
	public Usercards findByUserIdAndCardId(int userID, int cardID);
	
	public void delete(Usercards usercards);
}
