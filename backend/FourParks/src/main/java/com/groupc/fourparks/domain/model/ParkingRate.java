package com.groupc.fourparks.domain.model;

import com.groupc.fourparks.infraestructure.adapter.entity.ParkingEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.VehicleTypeEntity;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRate {
    private Long id;
    private float rate;
    private VehicleTypeEntity vehicleTypeId;
    private ParkingEntity parkingId;
}
