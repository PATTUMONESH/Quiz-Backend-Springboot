package com.example.quiz_app_backend.Dto;

import com.example.quiz_app_backend.Entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDto {
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private Long phno;
    private String address;
    private Long roleId;
}
