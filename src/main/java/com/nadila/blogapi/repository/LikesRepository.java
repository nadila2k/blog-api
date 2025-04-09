package com.nadila.blogapi.repository;

import com.nadila.blogapi.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {


    boolean existsByPost_IdAndUser_Id(Long postId, Long userId);

    Optional<Object> findByPost_IdAndUser_Id(Long postId, Long userId);


    List<Likes> findByPost_Id(Long postId);
}
