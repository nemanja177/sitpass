package com.ftn.fitpass.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.ftn.fitpass.DTO.FacilityCreateRequest;
import com.ftn.fitpass.model.Facility;

public interface FacilityService {

//	List<Facility> findAll();
//	
//	Optional<Facility> findById(Long id);
//	
//	Facility save(Facility facility);
//	
//	void deleteById(Long id);
	
	public Facility createFacility(FacilityCreateRequest request, List<MultipartFile> images, MultipartFile pdf) throws Exception;
	
}
