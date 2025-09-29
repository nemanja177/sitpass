package com.ftn.fitpass.services;

import java.util.List;

import com.ftn.fitpass.model.Description;

public interface DescriptionService {
	
	List<Description> findAll();
	
	Description findDescriptionById(Long id);
	
	Description save(Description description);
	
	void deleteById(Long id);

}
