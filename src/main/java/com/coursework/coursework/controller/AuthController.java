package com.coursework.coursework.controller;

import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.model.user.container.LoginUser;
import com.coursework.coursework.model.user.container.SignUpUser;
import com.coursework.coursework.service.impl.AuthenticationServiceImpl;
import com.coursework.coursework.service.interfaces.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// work
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/signin",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payload> authenticateUser(@Valid @RequestBody LoginUser loginRequest) {
        Payload payload = authenticationService.authenticateUser(loginRequest);

        if (payload.code() == 400) {
            return ResponseEntity
                    .badRequest()
                    .body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Payload> registerUser(@Valid @RequestBody SignUpUser signUpRequest) {
        Payload payload = authenticationService.registerUser(signUpRequest);
        if (payload.code() == 400) {
            return ResponseEntity
                    .badRequest()
                    .body(payload);
        }

        return ResponseEntity.ok(payload);
    }
}
