package com.devAlbaladejo.PokeCards.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devAlbaladejo.PokeCards.models.entities.Cards;

public interface ICardsDAO extends JpaRepository<Cards, Long>{

}
