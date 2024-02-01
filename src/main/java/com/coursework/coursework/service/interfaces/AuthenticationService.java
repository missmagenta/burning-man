package com.coursework.coursework.service.interfaces;

import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.model.user.container.LoginUser;
import com.coursework.coursework.model.user.container.SignUpUser;

public interface AuthenticationService {
    Payload registerUser(SignUpUser user);
    Payload authenticateUser(LoginUser user);
}
