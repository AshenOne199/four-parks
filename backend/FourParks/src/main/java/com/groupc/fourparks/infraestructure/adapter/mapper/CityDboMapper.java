package com.groupc.fourparks.infraestructure.adapter.mapper;



import com.groupc.fourparks.domain.model.City;
import com.groupc.fourparks.infraestructure.adapter.entity.CityEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityDboMapper {
    CityEntity toDbo(City creditCard);

    @InheritInverseConfiguration
    City toDomain(CityEntity cityEntity);
}
