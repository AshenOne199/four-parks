package com.groupc.fourparks.infraestructure.adapter.mapper;

import com.groupc.fourparks.domain.model.CreditCard;
import com.groupc.fourparks.infraestructure.adapter.entity.CreditCardEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCardDboMapper {

    CreditCardEntity toDbo(CreditCard creditCard);

    @InheritInverseConfiguration
    CreditCard toDomain(CreditCardEntity creditCardEntity);

}
