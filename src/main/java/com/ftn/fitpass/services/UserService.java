package com.ftn.fitpass.services;

import java.util.List;

import com.ftn.fitpass.model.User;
import com.ftn.fitpass.model.UserRequest;

public interface UserService {

	User save(UserRequest userRequest);
	
	User save(User korisnik);
	
	User findUserByUsername(String username);
	
	List<User> findAll();
	
	User findUserById(Integer id);
	
}
