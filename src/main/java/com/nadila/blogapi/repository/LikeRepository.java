package com.nadila.blogapi.repository;

import com.nadila.blogapi.Dto.LikeDto;
import com.nadila.blogapi.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes,Long> {


    boolean existsByPostAndUser(Long postId, Long authenticatedUserId);



    Optional<Object> findByPostAndUser(Long postId, Long userId);

    List<Likes> findByPost(Long post);
}
