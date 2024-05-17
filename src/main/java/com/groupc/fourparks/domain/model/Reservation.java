package com.groupc.fourparks.domain.model;

import com.groupc.fourparks.infraestructure.adapter.entity.ParkingSlotEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.VehicleTypeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    private Long id;
    private LocalDateTime reservationTime;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private Float totalPrice;
    private UserEntity user;
    private ParkingSlotEntity parkingSlot;
    private VehicleTypeEntity vehicleType;

}
