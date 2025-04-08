package com.nadila.blogapi.requests;


import com.nadila.blogapi.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequest {
    private String title;
    private String content;
    private String image_name;
    private String image_url;
    private PostStatus status;
    private Long category_id;
    private MultipartFile image;
}
