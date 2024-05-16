package com.groupc.fourparks.infraestructure.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private String jwt;
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String secondName;
    private String firstLastname;
    private String secondLastname;

    private boolean accountActive;
    private boolean accountBlocked;
    private int loginAttempts;
    private List<String> roleList;
    private CreditCardDto creditCard;

}
