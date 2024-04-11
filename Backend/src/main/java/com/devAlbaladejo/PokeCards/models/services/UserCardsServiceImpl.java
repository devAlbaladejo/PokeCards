package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devAlbaladejo.PokeCards.models.dao.IUserCardsDAO;
import com.devAlbaladejo.PokeCards.models.entities.Usercards;

@Service
public class UserCardsServiceImpl implements IUserCardsService {

	@Autowired
	private IUserCardsDAO userCardsDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usercards> findAll() {
		return userCardsDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Usercards userCard) {
		userCardsDAO.save(userCard);
	}

	@Override
	@Transactional(readOnly = true)
	public Usercards findByUserIdAndCardId(int userID, int cardID) {
		List<Usercards> allUserCards = userCardsDAO.findAll();
		Usercards userCard = allUserCards.stream().filter(e -> e.getUsers().getId() == userID && e.getCards().getId() == cardID).findAny().orElse(null);
		return userCard;
	}

	@Override
	@Transactional
	public void delete(Usercards userCard) {
		userCardsDAO.delete(userCard);
	}

}
