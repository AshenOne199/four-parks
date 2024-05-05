package com.groupc.fourparks.infraestructure.adapter.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.VehicleType;
import com.groupc.fourparks.infraestructure.adapter.entity.VehicleTypeEntity;

@Mapper(componentModel = "spring")
public interface VehicleTypeDboMapper {
    VehicleTypeEntity toDbo(VehicleType creditCard);

    @InheritInverseConfiguration
    VehicleType toDomain(VehicleTypeEntity creditCardEntity);
}
