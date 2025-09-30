package com.ftn.fitpass.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.fitpass.DTO.FacilityDocument;
import com.ftn.fitpass.model.Facility;
import com.ftn.fitpass.services.FacilityElasticService;
import com.ftn.fitpass.services.FacilityService;

@RestController
@RequestMapping("/api/facilities")
public class FacilityElasticController {

	
	@Autowired
    private FacilityService facilityService;
	
	@Autowired
    private FacilityElasticService facilityElasticService;

//    public FacilityElasticController(FacilityService facilityService,
//                                    FacilityElasticService facilityElasticService) {
//        this.facilityService = facilityService;
//        this.facilityElasticService = facilityElasticService;
//    }

    @PostMapping
    public ResponseEntity<Facility> createFacility(@RequestBody Facility facility) {
        Facility savedFacility = facilityService.save(facility);

        facilityElasticService.indexFacility(savedFacility.getId());

        return ResponseEntity.ok(savedFacility);
    }

    @PostMapping("/{id}/index")
    public ResponseEntity<?> reindexFacility(@PathVariable Long id) {
        FacilityDocument indexed = facilityElasticService.indexFacility(id);
        return ResponseEntity.ok(indexed);
    }

    @DeleteMapping("/{id}/index")
    public ResponseEntity<?> deleteIndex(@PathVariable Long id) {
        facilityElasticService.deleteFacilityIndex(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllFacilities() {
        return ResponseEntity.ok(facilityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFacilityById(@PathVariable Long id) {
        return facilityService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
