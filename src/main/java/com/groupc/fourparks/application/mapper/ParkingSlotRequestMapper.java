package com.groupc.fourparks.application.mapper;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.ParkingSlot;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingEntity;
import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;

@Mapper(componentModel = "spring")
public interface ParkingSlotRequestMapper {
    ParkingSlot toDomain(ParkingSlotRequest parkingSlotRequest);
    ParkingEntity map(Long id);
}
