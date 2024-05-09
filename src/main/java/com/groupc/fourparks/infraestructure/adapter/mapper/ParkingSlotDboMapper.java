package com.groupc.fourparks.infraestructure.adapter.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.ParkingSlot;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingSlotEntity;

@Mapper(componentModel = "spring")
public interface ParkingSlotDboMapper {
    ParkingSlotEntity toDbo(ParkingSlot parkingSlot);

    @InheritInverseConfiguration
    ParkingSlot toDomain(ParkingSlotEntity parkingSlotEntity);
}
