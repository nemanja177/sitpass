package com.ftn.fitpass.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.fitpass.model.Image;
import com.ftn.fitpass.repository.ImageRepository;
import com.ftn.fitpass.services.ImageService;


@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageRepository imageRepository;

	@Override
	public List<Image> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public Image findImageById(Long id) {
		return imageRepository.findImageById(id);
	}

	@Override
	public Image save(Image image) {
		return imageRepository.save(image);
	}

	@Override
	public void deleteById(Long id) {
		imageRepository.deleteById(id);
	}

}
