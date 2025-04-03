package com.nadila.blogapi.Dto;

import com.nadila.blogapi.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone_number;
    private Roles role;
    public String imageName;
    public String url;

}
