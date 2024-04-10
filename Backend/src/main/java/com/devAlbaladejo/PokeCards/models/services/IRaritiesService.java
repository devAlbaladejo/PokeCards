package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import com.devAlbaladejo.PokeCards.models.entities.Rarities;

public interface IRaritiesService {

	public List<Rarities> findAll();
	
	public Rarities findById(Long id);
}
