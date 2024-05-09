package com.groupc.fourparks.application.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.infraestructure.model.dto.ParkingRateDto;

@Mapper(componentModel = "spring")
public interface ParkingRateDtoMapper {
    ParkingRateDto toDto(ParkingRate parkingRate);

    @InheritInverseConfiguration
    ParkingRate toDomain(ParkingRateDto parkingRateDto);
}
