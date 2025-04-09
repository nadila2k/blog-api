package com.nadila.blogapi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {

    private Long id;
    private PostDto postDto;
    private UserDto userDto;
}
