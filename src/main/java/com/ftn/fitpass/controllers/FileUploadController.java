package com.ftn.fitpass.controllers;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.fitpass.DTO.FileUploadResponse;
import com.ftn.fitpass.services.FileStorageService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {
    
	@Autowired
	private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload/images")
    public ResponseEntity<List<FileUploadResponse>> uploadImages(
            @RequestParam("images") List<MultipartFile> images) {
        try {
            List<String> fileNames = fileStorageService.uploadImages(images);
            List<FileUploadResponse> responses = fileNames.stream()
                .map(fileName -> new FileUploadResponse(
                    fileName,
                    fileName,
                    fileStorageService.getFileUrl(fileName, "facility-images"),
                    null,
                    null))
                .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
        	System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/upload/pdf")
    public ResponseEntity<FileUploadResponse> uploadPdf(
            @RequestParam("pdf") MultipartFile pdf) {
        try {
            String fileName = fileStorageService.uploadPdf(pdf);
            FileUploadResponse response = new FileUploadResponse(
                fileName,
                fileName,
                fileStorageService.getFileUrl(fileName, "facility-pdfs"),
                null,
                null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
