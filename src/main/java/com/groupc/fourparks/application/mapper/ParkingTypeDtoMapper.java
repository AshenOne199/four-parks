package com.groupc.fourparks.application.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.ParkingType;
import com.groupc.fourparks.infraestructure.model.dto.ParkingTypeDto;

@Mapper(componentModel = "spring")
public interface ParkingTypeDtoMapper {
    ParkingTypeDto toDto(ParkingType parkingType);

    @InheritInverseConfiguration
    ParkingType toDomain(ParkingTypeDto parkingTypeDto);
}
