package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.CreditCard;
import com.groupc.fourparks.domain.model.User;

import java.util.List;

public interface CreditCardPort {

    void save(CreditCard creditCard, User user);
    void save(CreditCard creditCard);
    CreditCard  getCC(User user);
    List<CreditCard> getAllCC();
    void delete(CreditCard creditCard);

}
