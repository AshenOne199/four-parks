package com.groupc.fourparks.domain.model;

import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String email;
    private String password;
    private String ip;
    private String firstName;
    private String secondName;
    private String firstLastname;
    private String secondLastname;
    private boolean accountActive;
    private boolean accountBlocked;
    private int loginAttempts;
    private LocalDate updatedAt;
    private LocalDate createdAt;
    private Set<RoleEntity> roles = new HashSet<>();
    private List<String> roleList;
    private CreditCard creditCard;

}