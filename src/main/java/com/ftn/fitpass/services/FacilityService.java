package com.ftn.fitpass.services;

import java.util.List;
import java.util.Optional;

import com.ftn.fitpass.model.Facility;

public interface FacilityService {

	List<Facility> findAll();
	
	Optional<Facility> findById(Long id);
	
	Facility save(Facility facility);
	
	void deleteById(Long id);
}
