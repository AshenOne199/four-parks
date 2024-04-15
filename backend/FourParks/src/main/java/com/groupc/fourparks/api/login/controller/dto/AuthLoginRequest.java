package com.groupc.fourparks.api.login.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record AuthLoginRequest (@Email String email, @NotBlank String password) implements Serializable {
}
