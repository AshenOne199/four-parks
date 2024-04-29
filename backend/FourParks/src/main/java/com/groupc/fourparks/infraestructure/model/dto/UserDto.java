package com.groupc.fourparks.infraestructure.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String jwt;
    private String ip;
    private String email;
    private String password;
    private String firstName;
    private String secondName;
    private String firstLastname;
    private String secondLastname;

}
