package com.groupc.fourparks.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.groupc.fourparks.domain.model.ParkingSlot;

import com.groupc.fourparks.infraestructure.model.request.SlotToShow;

@Mapper(componentModel = "spring")
public interface SlotToShowMapper {
    @Mappings({
      @Mapping(target="id", source="id"),
      @Mapping(target="parkingId", source="parkingSlot.parkingId.id"),
      @Mapping(target="slotStatusId", source="parkingSlot.slotStatusId.status"),
      @Mapping(target="vehicleTypeId", source="parkingSlot.vehicleTypeId.type")
    })
    SlotToShow toShow(ParkingSlot parkingSlot);
}
