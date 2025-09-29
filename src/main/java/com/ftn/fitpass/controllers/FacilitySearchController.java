package com.ftn.fitpass.controllers;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import com.ftn.fitpass.DTO.FacilityDocument;
import com.ftn.fitpass.DTO.FacilitySearchRequest;
import com.ftn.fitpass.services.FacilitySearchService;

@RestController
@RequestMapping("/api/facilities")
public class FacilitySearchController {

    private final FacilitySearchService facilitySearchService;

    public FacilitySearchController(FacilitySearchService facilitySearchService) {
        this.facilitySearchService = facilitySearchService;
    }

    @PostMapping("/search")
    public SearchPage<FacilityDocument> searchFacilities(@RequestBody FacilitySearchRequest request, Pageable pageable) {
        return facilitySearchService.searchFacilities(request, pageable);
    }
}