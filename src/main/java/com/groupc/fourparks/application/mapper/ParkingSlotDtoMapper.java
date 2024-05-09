package com.groupc.fourparks.application.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.ParkingSlot;
import com.groupc.fourparks.infraestructure.model.dto.ParkingSlotDto;

@Mapper(componentModel = "spring")
public interface ParkingSlotDtoMapper {
    ParkingSlotDto toDto(ParkingSlot parkingSlot);

    @InheritInverseConfiguration
    ParkingSlot toDomain(ParkingSlotDto parkingSlotDto);
}
