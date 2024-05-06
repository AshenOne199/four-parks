package com.groupc.fourparks.infraestructure.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserNewPasswordRequest {

    @Email
    String email;

    @NotBlank
    String oldPassword;

    @NotBlank
    String newPassword;

    @NotBlank
    String confirmPassword;

}
