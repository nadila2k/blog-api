package com.nadila.blogapi.controller;

import com.nadila.blogapi.Dto.PostDto;
import com.nadila.blogapi.enums.PostStatus;
import com.nadila.blogapi.enums.ResponseStatus;
import com.nadila.blogapi.exception.ResourceNotFound;
import com.nadila.blogapi.requests.PostRequest;
import com.nadila.blogapi.requests.PostUpdateRequest;
import com.nadila.blogapi.response.ApiResponse;
import com.nadila.blogapi.service.Post.IiPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/post")
public class PostController {
    private final IiPostService iiPostService;

    @PostMapping
    public ResponseEntity<ApiResponse> createPost(@Validated @ModelAttribute PostRequest postRequest) {
        try {
            PostDto postDto = iiPostService.createPost(postRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Post created successfully", postDto));
        }catch (ResourceNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(),"" ));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.FAILED,e.getMessage(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePost(@Validated @ModelAttribute PostUpdateRequest postUpdateRequest, @PathVariable Long id) {
        try {
            PostDto postDto = iiPostService.updatePost(postUpdateRequest, id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Post updated successfully", postDto));
        } catch (ResourceNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.FAILED,e.getMessage(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long id) {
        try {
            iiPostService.deletePost(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Post deleted successfully", true));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        }
    }

    @PutMapping("/update/status/{postStatus}/{id}")
    public ResponseEntity<ApiResponse> updatePost(@PathVariable PostStatus postStatus, @PathVariable Long id) {
        try {
            PostDto postDto = iiPostService.UpdatePostStatus(postStatus, id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Post status updated successfully", postDto));
        } catch (ResourceNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        }
    }
    @GetMapping("/approve")
    public ResponseEntity<ApiResponse> getApprovePost() {
        try {
            List<PostDto> postDtos = iiPostService.getAllApprovePosts();
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Post approved successfully", postDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        }
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllPost() {
        try {
            List<PostDto> postDtos = iiPostService.getAllPosts();
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Posts found", postDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.FAILED,"image upload failed", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPostById(@PathVariable Long id) {
        try {
            PostDto postDto = iiPostService.getPostById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Post found", postDto));
        }catch (ResourceNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        }
    }

    @GetMapping("/getpost/userid")
    public ResponseEntity<ApiResponse> getPostByUserId() {
            try {
                List<PostDto> postDtos = iiPostService.getPostsByUserId();

                if (postDtos == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,"Posts not found", true));
                }
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Posts found", postDtos));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
            }
    }

    @GetMapping("/getpost/category/{id}")
    public ResponseEntity<ApiResponse> getPostsByCategoryId(@PathVariable Long id) {
        try {
            List<PostDto> postDtos = iiPostService.getPostsByCategoryId(id);
            if (postDtos == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,"Posts not found", true));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Posts found", postDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,e.getMessage(), ""));
        }
    }


}
