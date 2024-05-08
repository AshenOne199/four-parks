package com.groupc.fourparks.infraestructure.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.groupc.fourparks.domain.model.CreditCard;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.infraestructure.model.dto.CreditCardDto;
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


public class UserToShow {

    private Long id;
    private String email;
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


