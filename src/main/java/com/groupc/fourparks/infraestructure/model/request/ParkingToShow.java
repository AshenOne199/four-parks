package com.groupc.fourparks.infraestructure.model.request;

import java.time.LocalTime;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingToShow {
    Long id;
    String name;
    Integer available_slots;
    Integer total_slots;
    Integer car_slots;
    Integer bicycle_slots;
    Integer motorcycle_slots;
    Integer heavy_vehicle_slots;
    Boolean loyalty;
    String parkingType;
    String address;
    Float latitude;
    Float longitude;
    String city;
    LocalTime open_time;
    LocalTime close_time;
    AdminToShow adminToShow;
}
