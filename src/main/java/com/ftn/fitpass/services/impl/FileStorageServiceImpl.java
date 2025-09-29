package com.ftn.fitpass.services.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.fitpass.services.FileStorageService;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    private MinioClient minioClient;

    private String bucketName = "objekti";

    @Override
    public String uploadFile(MultipartFile file, String folder) throws Exception {
        String fileName = folder + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        return fileName;
    }

    @Override
    public String parsePdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}