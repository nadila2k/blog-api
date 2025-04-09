package com.nadila.blogapi.service.Post;

import com.nadila.blogapi.Dto.PostDto;
import com.nadila.blogapi.Dto.UserDto;
import com.nadila.blogapi.InbildObjects.ImageObj;
import com.nadila.blogapi.enums.PostStatus;
import com.nadila.blogapi.exception.ResourceNotFound;
import com.nadila.blogapi.model.Category;
import com.nadila.blogapi.model.Posts;
import com.nadila.blogapi.model.Users;
import com.nadila.blogapi.repository.CategoryRepository;
import com.nadila.blogapi.repository.PostRepository;
import com.nadila.blogapi.repository.UserRepository;
import com.nadila.blogapi.requests.PostRequest;
import com.nadila.blogapi.requests.PostUpdateRequest;
import com.nadila.blogapi.service.authService.GetAuthId;
import com.nadila.blogapi.service.category.CategoryService;
import com.nadila.blogapi.service.image.IimageService;
import com.nadila.blogapi.service.image.Imageservice;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements IiPostService{
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final GetAuthId getAuthId;
    private final CategoryRepository categoryRepository;
    private final Imageservice imageservice;

    @Override
    public PostDto createPost(PostRequest postRequest) {
        return Optional.ofNullable(postRequest)
                .map(post -> {
                    ImageObj imageObj = imageservice.uploadImage(post.getImage());
                    if (imageObj != null) {
                        Posts posts = new Posts();
                        posts.setTitle(post.getTitle());
                        posts.setContent(post.getContent());
                        posts.setImage_name(imageObj.getImageName());
                        posts.setImage_url(imageObj.getUrl());
                        posts.setStatus(PostStatus.PENDING);
                        Users user = userRepository.findById(getAuthId.getAuthenticatedUserId())
                                .orElseThrow(() -> new ResourceNotFound("User not found"));
                        posts.setUser(user);

                        Category category = categoryRepository.findById(post.getCategory_id())
                                .orElseThrow(() -> new ResourceNotFound("Category not found"));
                        posts.setCategory(category);

                        Posts savedPost = postRepository.save(posts);

                        // Map Posts -> PostDto
                        PostDto postDto = modelMapper.map(savedPost, PostDto.class);

                        // Manually set userDto
                        UserDto userDto = modelMapper.map(user, UserDto.class);
                        postDto.setUserDto(userDto);

                        return postDto;
                    } else {
                        throw new RuntimeException("Image upload failed");
                    }
                })
                .orElseThrow(() -> new IllegalArgumentException("Post request cannot be null"));
    }

    @Override
    public PostDto updatePost(PostUpdateRequest postUpdateRequest, Long id) {
        return postRepository.findById(id)
                .map(existsPost -> {
                    if (postUpdateRequest.getImage().isEmpty()) {
                        // No image update, just update other fields
                        existsPost.setTitle(postUpdateRequest.getTitle());
                        existsPost.setContent(postUpdateRequest.getContent());
                        existsPost.setImage_name(postUpdateRequest.getImage_name());
                        existsPost.setImage_url(postUpdateRequest.getImage_url());
                        existsPost.setStatus(PostStatus.PENDING);
                        existsPost.setCategory(categoryRepository.findById(postUpdateRequest.getCategory_id())
                                .orElseThrow(() -> new RuntimeException("Category not found")));
                        return modelMapper.map(postRepository.save(existsPost), PostDto.class);
                    } else {

                        imageservice.deleteImage(existsPost.getImage_name());
                        ImageObj imageObj = imageservice.uploadImage(postUpdateRequest.getImage());
                        if (imageObj != null) {
                            existsPost.setTitle(postUpdateRequest.getTitle());
                            existsPost.setContent(postUpdateRequest.getContent());
                            existsPost.setImage_name(imageObj.getImageName());
                            existsPost.setImage_url(imageObj.getUrl());
                            existsPost.setStatus(PostStatus.PENDING);
                            existsPost.setCategory(categoryRepository.findById(postUpdateRequest.getCategory_id())
                                    .orElseThrow(() -> new RuntimeException("Category not found")));
                            return modelMapper.map(postRepository.save(existsPost), PostDto.class);
                        } else {
                            throw new RuntimeException("Image upload failed");
                        }
                    }
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id)); // Post not found exception
    }

    @Override
    public void deletePost(Long id) {
       postRepository.findById(id)
               .ifPresentOrElse(postRepository::delete,() -> {throw new ResourceNotFound("Post not found with id " + id);});
    }

    @Override
    public PostDto UpdatePostStatus(PostStatus postStatus, Long id) {
        return postRepository.findById(id)
                .map(posts -> {
                    posts.setStatus(postStatus);
                    return modelMapper.map(postRepository.save(posts), PostDto.class);
                }).orElseThrow(() -> new ResourceNotFound("Post not found with id " + id));
    }

    @Override
    public List<PostDto> getAllApprovePosts() {
        return postRepository.findByStatus(PostStatus.APPROVED).stream()
                .map(post -> {
                    PostDto postDto = modelMapper.map(post, PostDto.class);

                    if (post.getUser() != null) {
                        UserDto userDto = modelMapper.map(post.getUser(), UserDto.class);
                        postDto.setUserDto(userDto);
                    }
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> {
                    PostDto postDto = modelMapper.map(post, PostDto.class);

                    if (post.getUser() != null) {
                        UserDto userDto = modelMapper.map(post.getUser(), UserDto.class);
                        postDto.setUserDto(userDto);
                    }
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        return postRepository.findById(id)
                .map(posts -> {
                    PostDto postDto = modelMapper.map(posts, PostDto.class);

                    if (posts.getUser() != null) {
                        UserDto userDto = modelMapper.map(posts.getUser(), UserDto.class);
                        postDto.setUserDto(userDto);
                    }

                    return postDto;
                })
                .orElseThrow(() -> new ResourceNotFound("Post not found with id " + id));
    }

    @Override
    public List<PostDto> getPostsByUserId() {
        return postRepository.findPostsByUser_Id(getAuthId.getAuthenticatedUserId())
                .stream().map(post -> {
                    PostDto postDto = modelMapper.map(post, PostDto.class);

                    if (post.getUser() != null) {
                        UserDto userDto = modelMapper.map(post.getUser(), UserDto.class);
                        postDto.setUserDto(userDto);
                    }
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long id) {
        return postRepository.findPostsByCategoryId(id).stream()
                .map(post -> {
                    PostDto dto = modelMapper.map(post, PostDto.class);
                    if (post.getUser() != null) {
                        UserDto userDto = modelMapper.map(post.getUser(), UserDto.class);
                        dto.setUserDto(userDto);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }




}
