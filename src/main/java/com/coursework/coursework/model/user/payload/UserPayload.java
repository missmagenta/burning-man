package com.coursework.coursework.model.user.payload;

import com.coursework.coursework.model.user.entity.UserRole;

public record UserPayload(
        String username,
        UserRole role,
        Integer omeId,
        String jwtToken,
        Integer personId,
        String personName
) {}

