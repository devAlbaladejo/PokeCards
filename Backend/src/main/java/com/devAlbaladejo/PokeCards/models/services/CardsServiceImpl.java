package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devAlbaladejo.PokeCards.models.dao.ICardsDAO;
import com.devAlbaladejo.PokeCards.models.entities.Cards;

@Service
public class CardsServiceImpl implements ICardsService{

	@Autowired
	private ICardsDAO cardsDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cards> findAll() {
		return cardsDAO.findAll(Sort.by(Sort.Direction.ASC,"id"));
	}

	@Override
	public void save(Cards card) {
		cardsDAO.save(card);		
	}

	@Override
	public Cards findById(Long id) {
		return cardsDAO.findById(id).orElse(null);
	}

	
}
