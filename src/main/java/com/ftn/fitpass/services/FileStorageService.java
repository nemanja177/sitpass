package com.ftn.fitpass.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {


    public String uploadFile(MultipartFile file, String folder) throws Exception;
        
    public String parsePdf(MultipartFile file) throws IOException;
  
}