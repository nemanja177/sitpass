package com.ftn.fitpass.services.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.fitpass.services.FileStorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService{

//    private MinioClient minioClient;
//
//    private String bucketName = "objekti";
//
//    @Override
//    public String uploadFile(MultipartFile file, String folder) throws Exception {
//        String fileName = folder + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
//
//        minioClient.putObject(
//                PutObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(fileName)
//                        .stream(file.getInputStream(), file.getSize(), -1)
//                        .contentType(file.getContentType())
//                        .build()
//        );
//
//        return fileName;
//    }
//
//    @Override
//    public String parsePdf(MultipartFile file) throws IOException {
//        try (PDDocument document = PDDocument.load(file.getInputStream())) {
//            PDFTextStripper stripper = new PDFTextStripper();
//            return stripper.getText(document);
//        }
//    }
//    
//	@Autowired
//	private MinioClient minioClient;
//	
//    private String bucketName = "fitpass-bucket";
//    
//    public FileStorageServiceImpl(MinioClient minioClient) {
//        this.minioClient = minioClient;
//    }
//    
//    public String uploadFile(MultipartFile file, String folder) throws Exception {
//        String fileName = generateFileName(file.getOriginalFilename());
//        String objectName = folder + "/" + fileName;
//        
//        validateFile(file);
//        
//        minioClient.putObject(
//            PutObjectArgs.builder()
//                .bucket(bucketName)
//                .object(objectName)
//                .stream(file.getInputStream(), file.getSize(), -1)
//                .contentType(file.getContentType())
//                .build()
//        );
//        
//        return fileName;
//    }
//    
//    public List<String> uploadFiles(List<MultipartFile> files, String folder) throws Exception {
//        List<String> fileNames = new ArrayList<>();
//        for (MultipartFile file : files) {
//            if (!file.isEmpty()) {
//                fileNames.add(uploadFile(file, folder));
//            }
//        }
//        return fileNames;
//    }
//    
//    public String generateFileName(String originalFileName) {
//        String extension = "";
//        int lastDotIndex = originalFileName.lastIndexOf('.');
//        if (lastDotIndex > 0) {
//            extension = originalFileName.substring(lastDotIndex);
//        }
//        return UUID.randomUUID().toString() + extension;
//    }
//    
//    public void validateFile(MultipartFile file) throws Exception {
//        if (file.getSize() > 10 * 1024 * 1024) {
//            throw new Exception("File size exceeds 10MB limit");
//        }
//        
//        String contentType = file.getContentType();
//        if (contentType == null) {
//            throw new Exception("Invalid file type");
//        }
//        
//        List<String> imageTypes = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/webp");
//        List<String> pdfTypes = Arrays.asList("application/pdf");
//        
//        if (!imageTypes.contains(contentType) && !pdfTypes.contains(contentType)) {
//            throw new Exception("Only images and PDF files are allowed");
//        }
//    }
//    
//    public String getFileUrl(String fileName, String folder) {
//        return "http://localhost:9000/" + bucketName + "/" + folder + "/" + fileName;
//    }
	
	private final MinioClient minioClient;
	
    private final String imagesBucket = "facility-images";
    private final String pdfsBucket = "facility-pdfs";
    
    public FileStorageServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }
    
    public String uploadImage(MultipartFile image) throws Exception {
        String fileName = generateFileName(image.getOriginalFilename());
        String objectName = fileName;
        
        validateImage(image);
        
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(imagesBucket)
                .object(objectName)
                .stream(image.getInputStream(), image.getSize(), -1)
                .contentType(image.getContentType())
                .build()
        );
        
        return fileName;
    }
    
    public String uploadPdf(MultipartFile pdf) throws Exception {
        String fileName = generateFileName(pdf.getOriginalFilename());
        String objectName = fileName;
        
        validatePdf(pdf);
        
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(pdfsBucket)
                .object(objectName)
                .stream(pdf.getInputStream(), pdf.getSize(), -1)
                .contentType(pdf.getContentType())
                .build()
        );
        
        return fileName;
    }
    
    public List<String> uploadImages(List<MultipartFile> images) throws Exception {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                fileNames.add(uploadImage(image));
            }
        }
        return fileNames;
    }
    
    public String generateFileName(String originalFileName) {
        String extension = "";
        int lastDotIndex = originalFileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            extension = originalFileName.substring(lastDotIndex);
        }
        return UUID.randomUUID().toString() + extension;
    }
    
    private void validateImage(MultipartFile image) throws Exception {
        if (image.getSize() > 10 * 1024 * 1024) {
            throw new Exception("Image size exceeds 10MB limit");
        }
        
        String contentType = image.getContentType();
        if (contentType == null) {
            throw new Exception("Invalid image type");
        }
        
        List<String> imageTypes = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/webp");
        
        if (!imageTypes.contains(contentType)) {
            throw new Exception("Only image files are allowed");
        }
    }
    
    private void validatePdf(MultipartFile pdf) throws Exception {
        if (pdf.getSize() > 10 * 1024 * 1024) {
            throw new Exception("PDF size exceeds 10MB limit");
        }
        
        String contentType = pdf.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            throw new Exception("Only PDF files are allowed");
        }
    }
	
    @Override
    public String getFileUrl(String fileName, String bucketName) {
        return "http://localhost:9000/" + bucketName + "/" + fileName;
    }
	
}