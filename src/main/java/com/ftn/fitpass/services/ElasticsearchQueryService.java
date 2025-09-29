package com.ftn.fitpass.services;

import java.io.IOException;
import java.util.List;

import com.ftn.fitpass.model.Objekat;

public interface ElasticsearchQueryService {


	public List<Objekat> searchCombined(String q, Integer minReview, Integer maxReview, Double minOcena, Double maxOcena) throws IOException;
	
	public List<Objekat> searchWithSpecialQuery(String query) throws IOException;
	
}
