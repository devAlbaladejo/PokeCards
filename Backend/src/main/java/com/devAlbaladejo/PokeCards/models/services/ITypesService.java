package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import com.devAlbaladejo.PokeCards.models.entities.Types;

public interface ITypesService {

	public List<Types> findAll();
	
	public Types findById(Long id);
}
