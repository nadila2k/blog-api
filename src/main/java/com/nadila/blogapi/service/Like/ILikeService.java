package com.nadila.blogapi.service.Like;


import com.nadila.blogapi.Dto.LikeDto;
import com.nadila.blogapi.model.Likes;

import java.util.List;

public interface ILikeService {
    LikeDto addLike(Long postId);
    void deleteLikeByPostAndUser(Long post_id, Long user_id);
    List<LikeDto> getLikesByPostId(Long postId);
}
