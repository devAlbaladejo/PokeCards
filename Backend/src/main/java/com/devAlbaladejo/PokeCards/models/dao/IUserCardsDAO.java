package com.devAlbaladejo.PokeCards.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devAlbaladejo.PokeCards.models.entities.Usercards;

public interface IUserCardsDAO extends JpaRepository<Usercards, Long>{

}
