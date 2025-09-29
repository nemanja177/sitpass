package com.ftn.fitpass.services;

import java.util.List;

import com.ftn.fitpass.model.Review;

public interface ReviewService {
	
	List<Review> findAll();
	
	Review findById(Long id);
	
	Review save(Review review);
	
	void deleteById(Long id);

}
