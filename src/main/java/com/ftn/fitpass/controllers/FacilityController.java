package com.ftn.fitpass.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.fitpass.DTO.FacilityCreateRequest;
import com.ftn.fitpass.model.Facility;
import com.ftn.fitpass.services.FacilityService;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {
    
    @Autowired
    private FacilityService facilityService;
    
    @PostMapping
    public ResponseEntity<Facility> createFacility(
            @RequestParam("name") String name,
            @RequestParam("city") String city,
            @RequestParam("address") String address,
            @RequestParam("description") String description,
            @RequestParam("disciplines") List<String> disciplines,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "pdf", required = false) MultipartFile pdf) {
        
        try {
            FacilityCreateRequest request = new FacilityCreateRequest();
            request.setName(name);
            request.setCity(city);
            request.setAddress(address);
            request.setDescription(description);
            request.setDisciplines(disciplines);
            
            Facility facility = facilityService.createFacility(request, images, pdf);
            System.out.println("FACILITY: " + facility.toString());
            return ResponseEntity.ok(facility);
        } catch (Exception e) {
        	System.out.println("ERROR: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
