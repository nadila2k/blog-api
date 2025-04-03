package com.nadila.blogapi.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        // Check connection by listing buckets
        try {
            s3Client.listBuckets();
            System.out.println("Successfully connected to Amazon S3");
        } catch (Exception e) {
            System.err.println("Failed to connect to Amazon S3: " + e.getMessage());
        }
        return s3Client;
    }

    public String getBucketName() {
        return bucketName;
    }
}
