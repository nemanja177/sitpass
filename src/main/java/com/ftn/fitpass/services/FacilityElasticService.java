package com.ftn.fitpass.services;

import com.ftn.fitpass.DTO.FacilityDocument;

public interface FacilityElasticService {

	public FacilityDocument indexFacility(Long facilityId);
	
	public void deleteFacilityIndex(Long facilityId);
	
	public void indexAllFacilities();
	
	public void deleteAllFacilityIndex();
	
}
