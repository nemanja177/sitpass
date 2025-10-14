package com.ftn.fitpass.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.fitpass.DTO.FacilityCreateRequest;
import com.ftn.fitpass.model.Description;
import com.ftn.fitpass.model.Facility;
import com.ftn.fitpass.model.Image;
import com.ftn.fitpass.repository.FacilityRepository;
import com.ftn.fitpass.services.FacilityElasticService;
import com.ftn.fitpass.services.FacilityService;
import com.ftn.fitpass.services.FileStorageService;

@Service
public class FacilityServiceImpl implements FacilityService {
	
//	@Autowired
//	private FacilityRepository facilityRepository;
//
//	@Override
//	public List<Facility> findAll() {
//		return facilityRepository.findAll();
//	}
//
//	@Override
//	public Optional<Facility> findById(Long id) {
//		return facilityRepository.findFacilityById(id);
//	}
//
//	@Override
//	public Facility save(Facility facility) {
//		return facilityRepository.save(facility);
//	}
//
//	@Override
//	public void deleteById(Long id) {
//		facilityRepository.deleteById(id);
//		
//	}
	
	@Autowired
    private FacilityRepository facilityRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private FacilityElasticService facilityElasticService;
    
    public Facility createFacility(FacilityCreateRequest request, 
                                 List<MultipartFile> images, 
                                 MultipartFile pdf) throws Exception {
        
        List<String> imageFileNames = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            imageFileNames = fileStorageService.uploadImages(images);
        }
        
        String pdfFileName = null;
        if (pdf != null && !pdf.isEmpty()) {
            pdfFileName = fileStorageService.uploadPdf(pdf);
        }
        
        Facility facility = new Facility();
        facility.setName(request.getName());
        facility.setCity(request.getCity());
        facility.setAddress(request.getAddress());
        facility.setDescription(request.getDescription());
        facility.setActive(true);
        facility.setDisciplines(request.getDisciplines());
        
        if (!imageFileNames.isEmpty()) {
            List<Image> imageEntities = imageFileNames.stream()
                .map(fileName -> {
                    Image image = new Image();
                    image.setServerFilename(fileName);
                    return image;
                })
                .collect(Collectors.toList());
            facility.setImages(imageEntities);
        }
        
        if (pdfFileName != null) {
            Description description = new Description();
            description.setServerFilename(pdfFileName);
            facility.setPdfDescription(description);
        }
        
        Facility savedFacility = facilityRepository.save(facility);
        
        facilityElasticService.indexFacility(savedFacility.getId());
        
        return savedFacility;
    }

}
