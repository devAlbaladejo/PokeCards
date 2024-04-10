package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import com.devAlbaladejo.PokeCards.models.entities.Users;

public interface IUsersService {

	public List<Users> findAll();
	
	public void save(Users user);
	
	public Users findById(Long id);
	
}
