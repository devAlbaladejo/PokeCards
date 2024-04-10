package com.devAlbaladejo.PokeCards.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devAlbaladejo.PokeCards.models.entities.Types;

public interface ITypesDAO extends JpaRepository<Types, Long>{

}
