package com.nadila.blogapi.service.Like;


import com.nadila.blogapi.Dto.LikeDto;
import com.nadila.blogapi.exception.ResourceNotFound;
import com.nadila.blogapi.model.Likes;
import com.nadila.blogapi.repository.LikeRepository;
import com.nadila.blogapi.repository.PostRepository;
import com.nadila.blogapi.repository.UserRepository;
import com.nadila.blogapi.service.authService.GetAuthId;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService{
    private final LikeRepository likeRepository;
    private final GetAuthId getAuthId;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public LikeDto addLike(Long postId) {
        Long userId = getAuthId.getAuthenticatedUserId();


        boolean hasLiked = likeRepository.existsByPostAndUser(postId, userId);


        if (!hasLiked) {
            Likes like = new Likes();
            like.setPost(postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFound("Post Not Found")));
            like.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFound("User Not Found")));


            return modelMapper.map(likeRepository.save(like), LikeDto.class);
        } else {
            deleteLikeByPostAndUser(postId, userId);
            throw new IllegalStateException("User has already liked this post");
        }
    }

    @Override
    public void deleteLikeByPostAndUser(Long post_id, Long user_id) {
        Likes like = (Likes) likeRepository.findByPostAndUser(post_id, user_id)
                .orElseThrow(() -> new ResourceNotFound("Like not found"));
        likeRepository.delete(like);
    }

    @Override
    public List<LikeDto> getLikesByPostId(Long postId) {
        return likeRepository.findByPost(postId)
                .stream().map(likes -> modelMapper.map(likes,LikeDto.class)).collect(Collectors.toList());
    }
}
