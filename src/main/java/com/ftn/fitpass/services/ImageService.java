package com.ftn.fitpass.services;

import java.util.List;

import com.ftn.fitpass.model.Image;

public interface ImageService {
	
	List<Image> findAll();
	
	Image findImageById(Long id);
	
	Image save(Image image);
	
	void deleteById(Long id);

}
