package com.roberto.support.report_documents.service;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.roberto.support.report_documents.config.AwsS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class StorageS3Service {

    @Autowired
    private AwsS3Client s3Client;

    public String uploadFile(File file, String context, String bucket) {
        String key = UUID.randomUUID() + "_" + context;
        s3Client.s3Client().putObject(new PutObjectRequest(bucket, key, file));
        return key;
    }
    
    public URL generateUrl(String bucket, String filename) {
        int timeExpiration = 1000 * 60 * 10;
        Date expiration = new Date(timeExpiration);
        URL url = s3Client.s3Client().generatePresignedUrl(bucket, filename, expiration);
        return url;
    }
}
