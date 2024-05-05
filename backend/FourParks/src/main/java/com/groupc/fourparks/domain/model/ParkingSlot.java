package com.groupc.fourparks.domain.model;

import com.groupc.fourparks.infraestructure.adapter.entity.ParkingEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.SlotStatusEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.VehicleTypeEntity;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlot {
    private Long id; 
    private ParkingEntity parkingId;
    private SlotStatusEntity slotStatusEntity;
    private VehicleTypeEntity vehicleTypeEntity;
}
