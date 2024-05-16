package com.groupc.fourparks.infraestructure.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParkingDto {
    Long id;
    UserDto admin;
    Integer available_slots;
    Boolean loyalty;
    String name;
    Integer total_slots;
    Integer car_slots;
    Integer bicycle_slots;
    Integer motorcycle_slots;
    Integer heavy_vehicle_slots;
    LocationDto location;
    OpeningHoursDto openingHours;
    ParkingTypeDto parkingType;
}
