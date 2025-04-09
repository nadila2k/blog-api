package com.nadila.blogapi.service.Post;

import com.nadila.blogapi.Dto.PostDto;
import com.nadila.blogapi.enums.PostStatus;
import com.nadila.blogapi.requests.PostRequest;
import com.nadila.blogapi.requests.PostUpdateRequest;
import org.hibernate.sql.Update;

import java.util.List;

public interface IiPostService {

    PostDto createPost(PostRequest postRequest);
    PostDto updatePost(PostUpdateRequest postUpdateRequest, Long id);
    void deletePost(Long id);
    PostDto UpdatePostStatus(PostStatus postStatus, Long id);
    List<PostDto> getAllApprovePosts();
    List<PostDto> getAllPosts();
    PostDto getPostById(Long id);
    List<PostDto> getPostsByUserId();
    List<PostDto> getPostsByCategoryId(Long id);
    void updateLikeCount(Long postId, int num);


}
