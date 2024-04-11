package com.devAlbaladejo.PokeCards.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devAlbaladejo.PokeCards.models.dao.IUsersDAO;
import com.devAlbaladejo.PokeCards.models.entities.Users;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private IUsersDAO usersDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = usersDAO
			.findOneByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("Username:" + username + " not exist."));
		
		return new UserDetailsImpl(user);
	}
}
