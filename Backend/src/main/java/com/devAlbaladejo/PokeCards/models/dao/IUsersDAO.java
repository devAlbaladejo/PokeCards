package com.devAlbaladejo.PokeCards.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devAlbaladejo.PokeCards.models.entities.Users;

public interface IUsersDAO extends JpaRepository<Users, Long>{
	
	Optional<Users> findOneByUsername(String username);
	
}
