package com.groupc.fourparks.infraestructure.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParkingSlotDto {
    Long id;
    ParkingDto parkingId;
    SlotStatusDto slotStatusId;
    VehicleTypeDto vehicleTypeId;
}
