package com.ftn.fitpass.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.fitpass.model.Objekat;
import com.ftn.fitpass.services.ElasticsearchQueryService;

@RestController
@RequestMapping("/api/pretraga")
public class ObjekatSearchController {

	@Autowired
	private ElasticsearchQueryService elasticsearchQueryService;

	@GetMapping("/combined")
	public List<Objekat> combinedSearch(
	        @RequestParam(required = false) String q,
	        @RequestParam(required = false) Integer minReview,
	        @RequestParam(required = false) Integer maxReview,
	        @RequestParam(required = false) Double minOcena,
	        @RequestParam(required = false) Double maxOcena
	) throws IOException {
	    return elasticsearchQueryService.searchCombined(q, minReview, maxReview, minOcena, maxOcena);
	}
	
	@GetMapping("/special")
	public List<Objekat> specialSearch(@RequestParam String q) throws IOException {
	    return elasticsearchQueryService.searchWithSpecialQuery(q);
	}
	
}
