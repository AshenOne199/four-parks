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
    private String email;
    private String jwt;
    private String rol;
    private String ip;
    private Long id;
    private String firstName;
    private String secondName;
    private String firstLastname;
    private String secondLastname;
}
