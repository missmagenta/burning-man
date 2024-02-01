package com.coursework.coursework.model.user.entity;

public record LoginedUser(
        String username,
        UserRole role,
        String jwtToken
) {}
