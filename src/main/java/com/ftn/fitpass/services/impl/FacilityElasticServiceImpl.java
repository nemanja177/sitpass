package com.ftn.fitpass.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.fitpass.DTO.FacilityDocument;
import com.ftn.fitpass.model.Facility;
import com.ftn.fitpass.model.Review;
import com.ftn.fitpass.repository.FacilityDocumentRepository;
import com.ftn.fitpass.repository.FacilityRepository;
import com.ftn.fitpass.repository.ReviewRepository;
import com.ftn.fitpass.services.FacilityDocumentMapperService;
import com.ftn.fitpass.services.FacilityElasticService;

import jakarta.transaction.Transactional;

@Service
public class FacilityElasticServiceImpl implements FacilityElasticService {

	@Autowired
	private FacilityRepository facilityRepository;
	
	@Autowired
    private ReviewRepository reviewRepository;
	
	@Autowired
    private FacilityDocumentMapperService facilityDocumentMapperService;
	
	@Autowired
    private FacilityDocumentRepository facilityDocumentRepository;
	

//    public FacilityElasticServiceImpl(FacilityRepository facilityRepository,
//                                 ReviewRepository reviewRepository,
//                                 FacilityDocumentMapperService facilityDocumentMapperService,
//                                 FacilityDocumentRepository facilityDocumentRepository) {
//        this.facilityRepository = facilityRepository;
//        this.reviewRepository = reviewRepository;
//        this.facilityDocumentMapperService = facilityDocumentMapperService;
//        this.facilityDocumentRepository = facilityDocumentRepository;
//    }

    @Transactional
    public FacilityDocument indexFacility(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found: " + facilityId));

        List<Review> reviewList = reviewRepository.findByFacilityId(facilityId);

        FacilityDocument document = facilityDocumentMapperService.toFacilityDocument(facility, reviewList);

        return facilityDocumentRepository.save(document);
    }
    
//    @Override
//    public void indexAllFacilities() {
//    	List<Facility> facilities = facilityRepository.findAll();
//        List<FacilityDocument> docs = facilities.stream().map(facility -> {
//        	System.out.println("Indexing facility id: " + facility.getId());
//            List<Review> reviews = reviewRepository.findByFacilityId(facility.getId());
//            return facilityDocumentMapperService.toFacilityDocument(facility, reviews);
//        }).collect(Collectors.toList());
//        facilityDocumentRepository.saveAll(docs);
//    }
    
    @Override
    public void indexAllFacilities() {
        List<Facility> facilities = facilityRepository.findAll();
        for (Facility facility : facilities) {
            System.out.println("Indexing facility id: " + facility.getId());
            List<Review> reviews = reviewRepository.findByFacilityId(facility.getId());
            FacilityDocument doc = facilityDocumentMapperService.toFacilityDocument(facility, reviews);
            try {
                facilityDocumentRepository.save(doc);
                System.out.println("Indexed facility id: " + facility.getId());
            } catch (Exception e) {
                System.err.println("Error indexing facility id " + facility.getId() + ": " + e.getMessage());
            }
        }
    }

    @Transactional
    public void deleteFacilityIndex(Long facilityId) {
        facilityDocumentRepository.deleteById(facilityId.toString());
    }
	
}
