package com.nadila.blogapi.repository;

import com.nadila.blogapi.Dto.PostDto;
import com.nadila.blogapi.enums.PostStatus;
import com.nadila.blogapi.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Posts,Long> {
    List<Posts> findByStatus(PostStatus status);

    List<Posts> findPostsByUser_Id(Long userId);



    @Query(value = "SELECT * FROM posts WHERE category_id = :categoryId", nativeQuery = true)
    List<Posts> findPostsByCategoryId(@Param("categoryId") Long categoryId);
}
