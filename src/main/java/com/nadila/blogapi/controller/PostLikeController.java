package com.nadila.blogapi.controller;

import com.nadila.blogapi.Dto.LikeDto;
import com.nadila.blogapi.enums.ResponseStatus;
import com.nadila.blogapi.exception.ResourceNotFound;
import com.nadila.blogapi.response.ApiResponse;
import com.nadila.blogapi.service.Like.ILikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/like")

public class PostLikeController {
    private final ILikeService iiLikeService;

    @PostMapping("/add/{postId}")
    public ResponseEntity<ApiResponse> addLike(@PathVariable Long postId) {
        try {
            LikeDto likeDto = iiLikeService.addLike(postId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(ResponseStatus.SUCCESS, "Like added successfully", likeDto));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(ResponseStatus.ERROR, e.getMessage(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ResponseStatus.ERROR, e.getMessage(), ""));
        }
    }

    @DeleteMapping("/delete/{postId}/{userId}")
    public ResponseEntity<ApiResponse> deleteLike(@PathVariable Long postId, @PathVariable Long userId) {
        try {
            iiLikeService.deleteLikeByPostAndUser(postId, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(ResponseStatus.SUCCESS, "Like deleted successfully", ""));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(ResponseStatus.ERROR, e.getMessage(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ResponseStatus.ERROR, e.getMessage(), ""));
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> getLikesByPost(@PathVariable Long postId) {
        try {
            List<LikeDto> likes = iiLikeService.getLikesByPostId(postId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(ResponseStatus.SUCCESS, "Likes retrieved successfully", likes));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(ResponseStatus.ERROR, e.getMessage(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(ResponseStatus.ERROR, e.getMessage(), ""));
        }
    }

}
