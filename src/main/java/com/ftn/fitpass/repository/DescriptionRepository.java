package com.ftn.fitpass.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.fitpass.model.Description;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {
	
	Description findDescriptionById(Long id);
	
}
