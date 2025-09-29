package com.ftn.fitpass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.fitpass.model.Objekat;

@Repository
public interface ObjekatRepository extends JpaRepository<Objekat, Long>{
	
	List<Objekat> findAll();
	
	Objekat findObjekatById(Long id);
	
}
