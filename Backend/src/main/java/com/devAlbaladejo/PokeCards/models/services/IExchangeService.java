package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import com.devAlbaladejo.PokeCards.models.entities.Exchanges;

public interface IExchangeService {

	public List<Exchanges> findAll();
	
	public void save(Exchanges exchange);
}
