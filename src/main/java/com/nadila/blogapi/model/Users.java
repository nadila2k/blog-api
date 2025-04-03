package com.nadila.blogapi.model;

import com.nadila.blogapi.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String phone_number;
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles role;
    public String imageName;
    public String url;


}
