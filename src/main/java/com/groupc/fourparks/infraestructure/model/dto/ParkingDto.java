package com.groupc.fourparks.infraestructure.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingDto {
    Long id;
    String name;
    Integer totalSlots;
    Integer availableSlots;
    Integer carSlots;
    Integer bicycleSlots;
    Integer motorcycleSlots;
    Integer heavyVehicleSlots;
    Boolean loyalty;
    LocationDto location;
    OpeningHoursDto openingHours;
    ParkingTypeDto parkingType;
    UserDto admin;
    List <ParkingRateDto> parkingRate;
}
