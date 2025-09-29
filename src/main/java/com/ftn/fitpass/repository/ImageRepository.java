package com.ftn.fitpass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.fitpass.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	
	Image findImageById(Long id);
	
}
