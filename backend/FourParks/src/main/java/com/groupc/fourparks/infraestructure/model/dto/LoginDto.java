package com.groupc.fourparks.infraestructure.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto{
    String email;
    String jwt;
    String rol;
    String ip;
    private String firstName;
    private String secondName;
    private String firstLastname;
    private String secondLastname;
}
