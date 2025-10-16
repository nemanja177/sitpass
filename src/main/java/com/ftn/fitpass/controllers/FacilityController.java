package com.ftn.fitpass.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.fitpass.DTO.FacilityCreateRequest;
import com.ftn.fitpass.model.Description;
import com.ftn.fitpass.model.Facility;
import com.ftn.fitpass.services.FacilityService;
import com.ftn.fitpass.services.MinioService;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {
    
    @Autowired
    private FacilityService facilityService;
    
    @Autowired
    private MinioService minioService;
    
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
    
    @GetMapping("/download/pdf/{facilityId}")
    public ResponseEntity<ByteArrayResource> downloadFacilityPdf(@PathVariable Long facilityId) {
        Facility facility = facilityService.findById(facilityId)
            .orElseThrow(() -> new RuntimeException("Facility not found"));
        
        Description pdfDesc = facility.getPdfDescription();
        if (pdfDesc == null || pdfDesc.getServerFilename() == null) {
            return ResponseEntity.notFound().build();
        }
        
        try {
            byte[] fileBytes = minioService.getFileBytes("facility-pdfs", pdfDesc.getServerFilename());
            ByteArrayResource resource = new ByteArrayResource(fileBytes);

            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + pdfDesc.getServerFilename() + "\"")
                .contentLength(fileBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
