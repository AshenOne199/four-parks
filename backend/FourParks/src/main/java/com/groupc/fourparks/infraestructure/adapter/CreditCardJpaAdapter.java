package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.CreditCard;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.CreditCardPort;
import com.groupc.fourparks.infraestructure.adapter.mapper.CreditCardDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.UserDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.CreditCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditCardJpaAdapter implements CreditCardPort {

    final private CreditCardRepository creditCardRepository;

    final private CreditCardDboMapper creditCardDboMapper;

    final private UserDboMapper userDboMapper;

    @Override
    public void save(CreditCard creditCard, User user) {
        var creditCardToSave = creditCardDboMapper.toDbo(creditCard);
        var userToSave = userDboMapper.toDbo(user);
        creditCardToSave.setUserId(userToSave);
        var creditCardSaved = creditCardRepository.save(creditCardToSave);
        creditCardDboMapper.toDomain(creditCardSaved);
    }
}
