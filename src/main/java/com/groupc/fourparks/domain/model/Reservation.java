package com.groupc.fourparks.domain.model;

import com.groupc.fourparks.infraestructure.adapter.entity.ParkingSlotEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
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

    public Float calculateTotal(Long minutes, Double rate){
        return (float) (minutes * rate);
    }

}
