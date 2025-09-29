package com.ftn.fitpass.services;

import java.util.List;

import com.ftn.fitpass.DTO.FacilityDocument;
import com.ftn.fitpass.model.Facility;
import com.ftn.fitpass.model.Review;

public interface FacilityDocumentMapperService {
	
	public FacilityDocument toFacilityDocument(Facility facility, List<Review> reviews);

}
