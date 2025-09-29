package com.ftn.fitpass.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.fitpass.model.Description;
import com.ftn.fitpass.repository.DescriptionRepository;
import com.ftn.fitpass.services.DescriptionService;

@Service
public class DescriptionServiceImpl implements DescriptionService {
	
	@Autowired
	private DescriptionRepository descriptionRepository;

	@Override
	public List<Description> findAll() {
		return descriptionRepository.findAll();
	}

	@Override
	public Description findDescriptionById(Long id) {
		return descriptionRepository.findDescriptionById(id);
	}

	@Override
	public Description save(Description description) {
		return descriptionRepository.save(description);
	}

	@Override
	public void deleteById(Long id) {
		descriptionRepository.deleteById(id);
	}

}
