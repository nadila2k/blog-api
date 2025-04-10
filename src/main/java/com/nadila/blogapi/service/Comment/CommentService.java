package com.nadila.blogapi.service.Comment;

import com.nadila.blogapi.Dto.CommentDto;
import com.nadila.blogapi.Dto.UserDto;
import com.nadila.blogapi.exception.ResourceNotFound;
import com.nadila.blogapi.model.Comment;
import com.nadila.blogapi.repository.CommentRepository;
import com.nadila.blogapi.repository.PostRepository;
import com.nadila.blogapi.repository.UserRepository;
import com.nadila.blogapi.requests.CommentRequest;
import com.nadila.blogapi.service.authService.GetAuthId;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final GetAuthId getAuthId;
    private final ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentRequest commentRequest, Long postId) {
        return Optional.ofNullable(commentRequest)
                .filter(commentRequest1 -> postRepository.existsById(postId))
                .map(commentRequest1 -> {
                    Comment comment = new Comment();
                    comment.setContent(commentRequest1.getContent());
                    comment.setPost(postRepository.findById(postId).get());
                    comment.setUser(userRepository.findById(getAuthId.getAuthenticatedUserId()).orElseThrow(() -> new ResourceNotFound("User not found")));
                    CommentDto dto = modelMapper.map(commentRepository.save(comment), CommentDto.class);
                    if (comment.getUser() != null) {
                        UserDto userDto = modelMapper.map(comment.getUser(), UserDto.class);
                        dto.setUserDto(userDto);
                    }
                    return dto;
                }).orElseThrow(() -> new ResourceNotFound("Post not found"));
    }

    @Override
    public CommentDto updateComment(CommentRequest commentRequest, Long commentId) {
        return Optional.ofNullable(commentRepository.findById(commentId))
                .map(  existComment -> {
                    Comment comment = new Comment();
                    comment.setContent(commentRequest.getContent());
                    return  modelMapper.map(commentRepository.save(comment), CommentDto.class);
                }).orElseThrow(() -> new ResourceNotFound("Comment not found"));
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        return commentRepository.findByPost_Id(postId)
                .stream()
                .map(comment -> {
                    CommentDto dto = modelMapper.map(comment, CommentDto.class);
                    if (comment.getUser() != null) {
                        UserDto userDto = modelMapper.map(comment.getUser(), UserDto.class);
                        dto.setUserDto(userDto);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.findById(commentId)
                .ifPresentOrElse(commentRepository::delete,() -> new ResourceNotFound("Comment not found"));
    }
}
