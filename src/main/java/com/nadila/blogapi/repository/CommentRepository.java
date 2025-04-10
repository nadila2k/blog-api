package com.nadila.blogapi.repository;

import com.nadila.blogapi.Dto.CommentDto;
import com.nadila.blogapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_Id(Long postId);
}
