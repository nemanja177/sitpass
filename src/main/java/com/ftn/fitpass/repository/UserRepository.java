package com.ftn.fitpass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.fitpass.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findUserByUsername(String username);
	
	List<User> findAll();
	
	User findUserById(Integer id);
}
