package com.nadila.blogapi.controller;

import com.nadila.blogapi.Dto.CommentDto;
import com.nadila.blogapi.enums.ResponseStatus;
import com.nadila.blogapi.exception.ResourceNotFound;
import com.nadila.blogapi.requests.CommentRequest;
import com.nadila.blogapi.response.ApiResponse;
import com.nadila.blogapi.service.Comment.ICommentService;
import com.nadila.blogapi.service.Like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/comment")
public class CommentController {

    private final ICommentService commentService;
    private final LikeService likeService;

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse> addComment(@Validated @RequestBody CommentRequest comment,@PathVariable Long id) {
        try {
            CommentDto commentDto = commentService.createComment(comment, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseStatus.SUCCESS,"Comment created successfully", commentDto));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.FAILED, e.getMessage(), ""));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.FAILED, e.getMessage(), ""));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateComment(@Validated @RequestBody CommentRequest comment,@PathVariable Long id) {
        try {
            CommentDto commentDto = commentService.updateComment(comment, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseStatus.SUCCESS,"Comment update successfully", commentDto));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.FAILED, e.getMessage(), ""));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.FAILED, e.getMessage(), ""));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Comment deleted successfully", null));
        }catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.FAILED, e.getMessage(), ""));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.FAILED, e.getMessage(), ""));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getComments(@PathVariable Long id) {
        try {
            List<CommentDto> commentDtos = commentService.getCommentsByPostId(id);
            if(commentDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.FAILED, String.format("Comment with id %d not found", id), ""));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS, "Comment found successfully", commentDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.FAILED, e.getMessage(), ""));
        }
    }
}
