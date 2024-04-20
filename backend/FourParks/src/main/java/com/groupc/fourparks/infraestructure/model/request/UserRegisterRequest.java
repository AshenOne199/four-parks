package com.groupc.fourparks.infraestructure.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterRequest{

    @Email
    String email;

    @NotBlank
    String firstName;

    @NotBlank
    String secondName;

    @NotBlank
    String firstLastname;

    @NotBlank
    String secondLastname;

    CreditCardRequest creditCard;

    @Valid
    @Size(max = 3, message = "El usuario no puede tener más de tres roles")
    List<String> roleList;
}
