package com.nadila.blogapi.service.Comment;

import com.nadila.blogapi.Dto.CommentDto;
import com.nadila.blogapi.requests.CommentRequest;

import java.util.List;

public interface ICommentService {

    CommentDto createComment(CommentRequest commentRequest,Long postId);
    CommentDto updateComment(CommentRequest commentRequest,Long commentId);
    List<CommentDto> getCommentsByPostId(Long postId);
    void deleteComment(Long commentId);

}
