package com.ftn.fitpass.services;

import java.util.List;

import com.ftn.fitpass.model.User;

public interface UserService {
	
	User save(User korisnik);
	
	User findUserByUsername(String username);
	
	List<User> findAll();
	
}
