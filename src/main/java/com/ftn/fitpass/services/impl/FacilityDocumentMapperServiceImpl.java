package com.ftn.fitpass.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ftn.fitpass.DTO.FacilityDocument;
import com.ftn.fitpass.model.Description;
import com.ftn.fitpass.model.Facility;
import com.ftn.fitpass.model.Image;
import com.ftn.fitpass.model.Review;
import com.ftn.fitpass.services.FacilityDocumentMapperService;
import com.ftn.fitpass.services.MinioService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.ByteArrayInputStream;

@Service
public class FacilityDocumentMapperServiceImpl implements FacilityDocumentMapperService {

	private final MinioService minioService;
    private final String bucketName = "facility-pdfs";

    public FacilityDocumentMapperServiceImpl(MinioService minioService) {
        this.minioService = minioService;
    }

    public FacilityDocument toFacilityDocument(Facility facility, List<Review> reviews) {
        FacilityDocument doc = new FacilityDocument();
        doc.setId(facility.getId().toString());
        doc.setName(facility.getName());
        doc.setCity(facility.getCity());
        doc.setAddress(facility.getAddress());
        doc.setDescription(facility.getDescription());
        doc.setDisciplines(facility.getDisciplines());
        doc.setImageFilenames(
            facility.getImages() == null ? List.of()
                : facility.getImages().stream().map(Image::getServerFilename).collect(Collectors.toList())
        );
        doc.setPdfDescriptionText(facility.getPdfDescription() == null ? "" : loadPdfText(facility.getPdfDescription()));
        doc.setPdfFileName(facility.getPdfDescription().getServerFilename());
//        doc.setPdfDescriptionText("");
        System.out.println("FAJL: " + facility.getPdfDescription().getServerFilename());
        System.out.println("TEXT IZ FAJLA:" + loadPdfText(facility.getPdfDescription()));
        if (reviews == null || reviews.isEmpty()) {
            doc.setReviewCount(0);
            doc.setAvgEquipmentGrade(0);
            doc.setAvgStaffGrade(0);
            doc.setAvgHygieneGrade(0);
            doc.setAvgSpaceGrade(0);
        } else {
            doc.setReviewCount(reviews.size());
            doc.setAvgEquipmentGrade(reviews.stream().mapToInt(Review::getEquipmentGrade).average().orElse(0));
            doc.setAvgStaffGrade(reviews.stream().mapToInt(Review::getStaffGrade).average().orElse(0));
            doc.setAvgHygieneGrade(reviews.stream().mapToInt(Review::getHygieneGrade).average().orElse(0));
            doc.setAvgSpaceGrade(reviews.stream().mapToInt(Review::getSpaceGrade).average().orElse(0));
        }
        return doc;
    }

    private String loadPdfText(Description description) {
        try {
            byte[] pdfData = minioService.getFileBytes(bucketName, description.getServerFilename());
            try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfData))) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        } catch (Exception e) {
            System.err.println("Error reading PDF text from MinIO: " + e.getMessage());
            return "";
        }
    }
	
}
