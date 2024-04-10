package com.devAlbaladejo.PokeCards.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devAlbaladejo.PokeCards.models.dao.ITypesDAO;
import com.devAlbaladejo.PokeCards.models.entities.Types;

@Service
public class TypesServiceImpl implements ITypesService{

	@Autowired
	private ITypesDAO typesDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Types> findAll() {
		return typesDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Types findById(Long id) {
		return typesDAO.findById(id).orElse(null);
	}

}
