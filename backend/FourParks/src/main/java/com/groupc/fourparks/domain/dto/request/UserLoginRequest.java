package com.groupc.fourparks.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UserLoginRequest(@Email String email, @NotBlank String password) implements Serializable {
}
