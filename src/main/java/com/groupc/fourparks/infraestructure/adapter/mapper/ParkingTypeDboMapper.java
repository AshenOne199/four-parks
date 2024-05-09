package com.groupc.fourparks.infraestructure.adapter.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.ParkingType;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingTypeEntity;

@Mapper(componentModel = "spring")
public interface ParkingTypeDboMapper {
    ParkingTypeEntity toDbo(ParkingType parkingType);

    @InheritInverseConfiguration
    ParkingType toDomain(ParkingTypeEntity parkingTypeEntity);
}
