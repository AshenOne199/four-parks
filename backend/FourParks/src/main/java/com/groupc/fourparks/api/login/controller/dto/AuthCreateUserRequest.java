package com.groupc.fourparks.api.login.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record AuthCreateUserRequest(
        @Email String email,
        @NotBlank String password,
        @Valid AuthCreateRoleRequest roleRequest,
        @NotBlank String firstName,
        @NotBlank String secondName,
        @NotBlank String firstLastname,
        @NotBlank String secondLastname){

}
