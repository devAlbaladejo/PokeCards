package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import com.devAlbaladejo.PokeCards.models.entities.ExchangeOffers;

public interface IExchangeOffersService {

	public List<ExchangeOffers> findAll();
	
	public ExchangeOffers findById(Long id);
	
	public void save(ExchangeOffers exchangeOffers);
	
	public void deleteById(Long id);
}
