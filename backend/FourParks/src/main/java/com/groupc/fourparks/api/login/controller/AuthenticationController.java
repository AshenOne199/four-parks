package com.groupc.fourparks.api.login.controller;

import com.groupc.fourparks.api.login.controller.dto.AuthCreateUserRequest;
import com.groupc.fourparks.api.login.controller.dto.AuthLoginRequest;
import com.groupc.fourparks.api.login.controller.dto.AuthResponse;
import com.groupc.fourparks.api.login.model.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationController(final UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest) {
        return new ResponseEntity<>(this.userDetailsService.createUser(authCreateUserRequest), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailsService.loginUser(userRequest), HttpStatus.OK);
    }

}
