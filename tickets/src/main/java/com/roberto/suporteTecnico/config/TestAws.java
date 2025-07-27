package com.roberto.suporteTecnico.config;

import com.amazonaws.services.s3.model.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class TestAws implements CommandLineRunner  {


    @Autowired
    AwsS3Client awsS3Client;


    @Override
    public void run(String... args) throws Exception {

      


     

    }
}
