package com.ftn.fitpass.services;

public interface MinioService {

    public byte[] getFileBytes(String bucketName, String filename) throws Exception;
 
}
