package com.ftn.fitpass.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.fitpass.model.User;
import com.ftn.fitpass.repository.UserRepository;
import com.ftn.fitpass.services.UserService;



@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User korisnik) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
