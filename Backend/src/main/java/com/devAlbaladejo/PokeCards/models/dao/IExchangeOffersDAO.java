package com.devAlbaladejo.PokeCards.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devAlbaladejo.PokeCards.models.entities.ExchangeOffers;

public interface IExchangeOffersDAO extends JpaRepository<ExchangeOffers, Long>{

}
