package com.nadila.blogapi.response;

import com.nadila.blogapi.Dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private UserDto user;

}
