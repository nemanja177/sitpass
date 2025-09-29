package com.ftn.fitpass.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.fitpass.model.Facility;
import com.ftn.fitpass.repository.FacilityRepository;
import com.ftn.fitpass.services.FacilityService;

@Service
public class FacilityServiceImpl implements FacilityService {
	
	@Autowired
	private FacilityRepository facilityRepository;

	@Override
	public List<Facility> findAll() {
		return facilityRepository.findAll();
	}

	@Override
	public Optional<Facility> findById(Long id) {
		return facilityRepository.findFacilityById(id);
	}

	@Override
	public Facility save(Facility facility) {
		return facilityRepository.save(facility);
	}

	@Override
	public void deleteById(Long id) {
		facilityRepository.deleteById(id);
		
	}

}
