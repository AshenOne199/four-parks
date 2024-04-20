package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.infraestructure.model.dto.CreditCardDto;

public interface CreditCardService {
    boolean validateCreditCard(CreditCardDto creditCard);
}
