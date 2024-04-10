package com.devAlbaladejo.PokeCards.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devAlbaladejo.PokeCards.models.entities.Rarities;

public interface IRaritiesDAO extends JpaRepository<Rarities, Long>{

}
