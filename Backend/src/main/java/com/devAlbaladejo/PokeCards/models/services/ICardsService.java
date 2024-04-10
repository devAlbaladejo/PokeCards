package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import com.devAlbaladejo.PokeCards.models.entities.Cards;

public interface ICardsService {

	public List<Cards> findAll();
	
	public void save(Cards card);
	
	public Cards findById(Long id);
}
