package com.devAlbaladejo.PokeCards.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devAlbaladejo.PokeCards.models.entities.Exchanges;

public interface IExchangesDAO extends JpaRepository<Exchanges, Long>{

}
