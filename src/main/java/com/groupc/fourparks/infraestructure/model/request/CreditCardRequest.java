package com.groupc.fourparks.infraestructure.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditCardRequest {

    private String cardNumber;
    private String expirationDate;
    private String cvv;

}
