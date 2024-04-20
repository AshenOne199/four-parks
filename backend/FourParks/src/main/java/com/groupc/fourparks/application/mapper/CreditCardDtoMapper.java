package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.CreditCard;
import com.groupc.fourparks.infraestructure.model.dto.CreditCardDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCardDtoMapper {

    CreditCardDto toDto(CreditCard creditCard);

    @InheritInverseConfiguration
    CreditCard toDomain(CreditCardDto creditCardDto);

}
