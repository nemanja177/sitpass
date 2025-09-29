package com.ftn.fitpass.services.impl;

import io.minio.MinioClient;
import io.minio.GetObjectArgs;
import org.springframework.stereotype.Service;

import com.ftn.fitpass.services.MinioService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    public MinioServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public byte[] getFileBytes(String bucketName, String filename) throws Exception {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(filename)
                        .build())) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }
}
