package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.CreditCard;
import com.groupc.fourparks.domain.model.User;

public interface CreditCardPort {

    void save(CreditCard creditCard, User user);

}
