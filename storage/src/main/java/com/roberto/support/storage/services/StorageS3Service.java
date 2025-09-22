package com.roberto.support.storage.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.roberto.support.storage.config.AwsS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StorageS3Service {

    private final AwsS3Client s3Client;

    protected String uploadFile(File file, String context, String bucket) {
        String key = UUID.randomUUID() + "_" + context;
        s3Client.s3Client().putObject(new PutObjectRequest(bucket, key, file));
        return key;
    }
    
    protected URL generateUrl(String bucket, String contentType,  String filename) {
        Date expiration = new Date(1000 * 60 * 10);

        ResponseHeaderOverrides headers = new ResponseHeaderOverrides()
                .withContentType(contentType)
                .withContentDisposition("attachment; filename=\"" + filename + "\"");

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, filename)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration)
                .withResponseHeaders(headers);

        return s3Client.s3Client().generatePresignedUrl(request);
    }

    protected void removeFile(String key, String bucket) {

        DeleteObjectRequest deleteObject = new DeleteObjectRequest(bucket, key);

        s3Client.s3Client().deleteObject(deleteObject);
    }
}
