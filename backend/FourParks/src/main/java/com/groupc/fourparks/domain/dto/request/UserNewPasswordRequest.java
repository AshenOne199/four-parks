package com.groupc.fourparks.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserNewPasswordRequest (@Email String email, @NotBlank String oldPassword, @NotBlank String newPassword, @NotBlank String confirmPassword) {
}
