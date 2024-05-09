package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.infraestructure.model.dto.ParkingDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParkingDtoMapper {
    ParkingDto toDto(Parking parking);

    @InheritInverseConfiguration
    Parking toDomain(ParkingDto parkingDto);
}
