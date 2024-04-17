package com.groupc.fourparks.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record UserRegisterRequest(
        @Email String email,
        @Valid AuthCreateRoleRequest roleRequest,
        @NotBlank String firstName,
        @NotBlank String secondName,
        @NotBlank String firstLastname,
        @NotBlank String secondLastname){

}
