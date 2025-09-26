package com.ftn.fitpass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.fitpass.model.SportskiObjekat;

@Repository
public interface SportskiObjekatRepository extends JpaRepository<SportskiObjekat, Integer>{
	
	
	
}
