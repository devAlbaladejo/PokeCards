package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devAlbaladejo.PokeCards.models.dao.IExchangesDAO;
import com.devAlbaladejo.PokeCards.models.entities.Exchanges;

@Service
public class ExchangesServiceImpl implements IExchangeService{

	@Autowired
	private IExchangesDAO exchangesDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Exchanges> findAll() {
		return exchangesDAO.findAll();
	}
	
	@Override
	public void save(Exchanges exchange) {
		exchangesDAO.save(exchange);
	}
}
