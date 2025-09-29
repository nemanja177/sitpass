package com.ftn.fitpass.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;

import com.ftn.fitpass.DTO.FacilityDocument;
import com.ftn.fitpass.DTO.FacilitySearchRequest;

public interface FacilitySearchService {

//	public SearchHits<FacilityDocument> searchFacilities(FacilitySearchRequest request);
	
	public SearchPage<FacilityDocument> searchFacilities(FacilitySearchRequest request, Pageable pageable);
	
}
