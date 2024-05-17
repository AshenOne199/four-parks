package com.groupc.fourparks.infraestructure.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingSlotEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.VehicleTypeEntity;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDto {

    private Long id;
    private LocalDateTime reservationTime;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private Float totalPrice;
    private UserEntity user;
    private ParkingSlotEntity parkingSlot;
    private VehicleTypeEntity vehicleType;

}
