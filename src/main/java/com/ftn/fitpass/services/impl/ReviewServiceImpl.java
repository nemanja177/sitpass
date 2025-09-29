package com.ftn.fitpass.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.fitpass.model.Review;
import com.ftn.fitpass.repository.ReviewRepository;
import com.ftn.fitpass.services.ReviewService;

public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}

	@Override
	public Review findById(Long id) {
		return reviewRepository.findReviewById(id);
	}

	@Override
	public Review save(Review review) {
		return reviewRepository.save(review);
	}

	@Override
	public void deleteById(Long id) {
		reviewRepository.deleteById(id);
		
	}

}
