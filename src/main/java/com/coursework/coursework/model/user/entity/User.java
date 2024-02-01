package com.coursework.coursework.model.user.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {
    private Integer id;
    private Integer personId;
    private String username;
    private String password;
    private UserRole role;
    private String jwtToken;
}
