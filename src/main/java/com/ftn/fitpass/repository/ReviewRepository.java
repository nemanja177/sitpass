package com.ftn.fitpass.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.fitpass.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
	Review findReviewById(Long id);
	
	List<Review> findByFacilityId(Long id);
	
}
