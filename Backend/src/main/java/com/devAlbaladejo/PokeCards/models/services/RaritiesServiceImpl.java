package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devAlbaladejo.PokeCards.models.dao.IRaritiesDAO;
import com.devAlbaladejo.PokeCards.models.entities.Rarities;

@Service
public class RaritiesServiceImpl implements IRaritiesService{

	@Autowired
	private IRaritiesDAO raritiesDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Rarities> findAll() {
		return raritiesDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Rarities findById(Long id) {
		return raritiesDAO.findById(id).orElse(null);
	}

}
