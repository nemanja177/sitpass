package com.ftn.fitpass.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {


//    public String uploadFile(MultipartFile file, String folder) throws Exception;
//        
//    public String parsePdf(MultipartFile file) throws IOException;
//    
//    public List<String> uploadFiles(List<MultipartFile> files, String folder) throws Exception;
//  
//    public String generateFileName(String originalFileName);
//    
//    public void validateFile(MultipartFile file) throws Exception;
//    
//    public String getFileUrl(String fileName, String folder);
	
	
	public String uploadImage(MultipartFile image) throws Exception;
	public String uploadPdf(MultipartFile pdf) throws Exception;
	public List<String> uploadImages(List<MultipartFile> images) throws Exception;
	public String generateFileName(String originalFileName);
	public String getFileUrl(String fileName, String bucketName);
}