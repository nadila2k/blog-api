package com.nadila.blogapi.service.Like;


import com.nadila.blogapi.Dto.LikeDto;
import com.nadila.blogapi.Dto.PostDto;
import com.nadila.blogapi.Dto.UserDto;
import com.nadila.blogapi.exception.ResourceNotFound;
import com.nadila.blogapi.model.Likes;

import com.nadila.blogapi.repository.LikesRepository;
import com.nadila.blogapi.repository.PostRepository;
import com.nadila.blogapi.repository.UserRepository;
import com.nadila.blogapi.service.Post.IiPostService;
import com.nadila.blogapi.service.authService.GetAuthId;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService{
    private final LikesRepository likeRepository;
    private final GetAuthId getAuthId;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final IiPostService  iiPostService;

    @Override
    public LikeDto addLike(Long postId) {
        Long userId = getAuthId.getAuthenticatedUserId();


        boolean hasLiked = likeRepository.existsByPost_IdAndUser_Id(postId, userId);


        if (!hasLiked) {
            Likes like = new Likes();
            like.setPost(postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFound("Post Not Found")));
            like.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFound("User Not Found")));

            iiPostService.updateLikeCount(postId, 1);
            Likes savedLike = likeRepository.save(like);

            // Map to LikeDto
            LikeDto likeDto = modelMapper.map(savedLike, LikeDto.class);

            // Additional mapping for userDto if needed (e.g., User info)
            if (savedLike.getUser() != null) {
                PostDto postDto = modelMapper.map(savedLike.getPost(), PostDto.class);
                UserDto userDto = modelMapper.map(savedLike.getUser(), UserDto.class);
                likeDto.setPostDto(postDto);
                likeDto.setUserDto(userDto);
            }

            return likeDto;
        } else {
            deleteLikeByPostAndUser(postId, userId);
            throw new IllegalStateException("User has already liked this post");
        }
    }

    @Override
    public void deleteLikeByPostAndUser(Long post_id, Long user_id) {

        iiPostService.updateLikeCount(post_id, -1);
        Likes like = (Likes) likeRepository.findByPost_IdAndUser_Id(post_id, user_id)
                .orElseThrow(() -> new ResourceNotFound("Like not found"));

        likeRepository.delete(like);
    }

    public List<LikeDto> getLikesByPostId(Long postId) {
        List<Likes> likes = likeRepository.findByPost_Id(postId);

        // Assuming all likes are for the same post, map the postDto once
        PostDto postDto = modelMapper.map(likes.get(0).getPost(), PostDto.class);

        return likes.stream()
                .map(like -> {
                    LikeDto likeDto = modelMapper.map(like, LikeDto.class);

                    // Set the pre-mapped PostDto
                    likeDto.setPostDto(postDto);

                    // Map User to UserDto and set it
                    UserDto userDto = modelMapper.map(like.getUser(), UserDto.class);
                    likeDto.setUserDto(userDto);

                    return likeDto;
                })
                .collect(Collectors.toList());
    }
}
