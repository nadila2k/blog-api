package com.nadila.blogapi.service.image;

import com.nadila.blogapi.InbildObjects.ImageObj;
import org.springframework.web.multipart.MultipartFile;

public interface IimageService {

    ImageObj uploadImage(MultipartFile file);
    void deleteImage(String name);
}
