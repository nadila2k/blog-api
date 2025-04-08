package com.nadila.blogapi.Dto;

import com.nadila.blogapi.enums.PostStatus;
import com.nadila.blogapi.model.Category;
import com.nadila.blogapi.model.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private String image_name;
    private String image_url;
    private PostStatus status;
    private int like_count;
    private int comment_count;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto userDto;
    private Category category;

}
