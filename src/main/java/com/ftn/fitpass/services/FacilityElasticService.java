package com.ftn.fitpass.services;

import java.util.List;

import com.ftn.fitpass.DTO.FacilityDocument;
import com.ftn.fitpass.model.Facility;

public interface FacilityElasticService {

	public FacilityDocument indexFacility(Long facilityId);
	
	public void deleteFacilityIndex(Long facilityId);
	
	public void indexAllFacilities();
	
}
