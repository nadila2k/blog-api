package com.nadila.blogapi.service.image;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.nadila.blogapi.InbildObjects.ImageObj;
import com.nadila.blogapi.config.S3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Imageservice implements IimageService {

    private final AmazonS3 amazonS3;
    private final S3Config s3Config;

    @Override
    public ImageObj uploadImage(MultipartFile file) {



        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.isEmpty()) {
                return new ImageObj("Error", "Error: File name is empty.");
            }

            // Upload file to S3 with content type
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            amazonS3.putObject(new PutObjectRequest(s3Config.getBucketName(), fileName, file.getInputStream(), metadata));

            // Generate file URL
            String fileUrl = amazonS3.getUrl(s3Config.getBucketName(), fileName).toString();

            return new ImageObj(fileName, fileUrl);
        } catch (AmazonServiceException e) {
            return new ImageObj("Error", "Error uploading file: AWS Service Exception - " + e.getErrorMessage());
        } catch (SdkClientException e) {
            return new ImageObj("Error", "Error uploading file: AWS SDK Client Exception - " + e.getMessage());
        } catch (IOException e) {
            return new ImageObj("Error", "Error uploading file: I/O Exception - " + e.getMessage());
        } catch (Exception e) {
            return new ImageObj("Error", "Error uploading file: " + e.getMessage());
        }
    }

    @Override
    public void deleteImage(String name) {

    }
}
