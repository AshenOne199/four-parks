package com.groupc.fourparks.domain.model;

import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    private Long id;
    private UserEntity userId;
    private String cardNumber;
    private String expirationDate;
    private String cvv;

}
