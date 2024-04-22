package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devAlbaladejo.PokeCards.models.dao.IExchangeOffersDAO;
import com.devAlbaladejo.PokeCards.models.entities.ExchangeOffers;

@Service
public class ExchangeOffersServiceImpl implements IExchangeOffersService {

	@Autowired
	private IExchangeOffersDAO exchangeOffersDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<ExchangeOffers> findAll() {
		return exchangeOffersDAO.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExchangeOffers findById(Long id) {
		return exchangeOffersDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(ExchangeOffers exchangeOffers) {
		exchangeOffersDAO.save(exchangeOffers);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		exchangeOffersDAO.deleteById(id);		
	}

}
