package com.groupc.fourparks.domain.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String secondName;
    private String firstLastname;
    private String secondLastname;
    private boolean isActive;

}
