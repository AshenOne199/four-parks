package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.CreditCard;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.CreditCardPort;
import com.groupc.fourparks.infraestructure.adapter.entity.CreditCardEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.CreditCardDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.UserDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.CreditCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        creditCardRepository.save(creditCardToSave);
    }

  @Override
    public void save(CreditCard creditCard)
    {
        creditCardRepository.save(creditCardDboMapper.toDbo(creditCard));
    }

    @Override
    public CreditCard  getCC(User user) {
        CreditCard creditCard = new CreditCard();
        List<CreditCardEntity> cardsReceiver = creditCardRepository.findAll();
        for (CreditCardEntity cc : cardsReceiver) {
            if (Objects.equals(cc.getUserId().getId(), user.getId()))
            {
                creditCard =creditCardDboMapper.toDomain(cc);

            }
        }

        return creditCard;
    }
}
