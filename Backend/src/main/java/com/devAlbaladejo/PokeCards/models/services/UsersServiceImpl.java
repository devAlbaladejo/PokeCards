package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devAlbaladejo.PokeCards.models.dao.IUsersDAO;
import com.devAlbaladejo.PokeCards.models.entities.Users;

@Service
public class UsersServiceImpl implements IUsersService{

	@Autowired
	private IUsersDAO usersDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Users> findAll() {
		return usersDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Users user) {
		usersDAO.save(user);
	}
}
