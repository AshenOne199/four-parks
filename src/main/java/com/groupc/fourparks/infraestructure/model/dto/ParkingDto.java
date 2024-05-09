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
    String available_slots;
    String loyalty;
    String name;
    String total_slots;
    LocationDto location;
    OpeningHoursDto openingHours;
    ParkingTypeDto parkingType;
}
