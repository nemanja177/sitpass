package com.ftn.fitpass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.fitpass.model.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
	
	Optional<Facility> findFacilityById(Long id);
    
}