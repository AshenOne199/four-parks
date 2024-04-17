package com.groupc.fourparks.infraestructure.rest.controller;

import com.groupc.fourparks.domain.dto.request.UserRegisterRequest;
import com.groupc.fourparks.domain.dto.request.UserLoginRequest;
import com.groupc.fourparks.domain.dto.AuthResponse;
import com.groupc.fourparks.application.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationController(final UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        return new ResponseEntity<>(this.userDetailsService.createUser(userRegisterRequest), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        return new ResponseEntity<>(this.userDetailsService.loginUser(userLoginRequest), HttpStatus.OK);
    }

}
