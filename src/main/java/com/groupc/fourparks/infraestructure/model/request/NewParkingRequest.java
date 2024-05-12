package com.groupc.fourparks.infraestructure.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewParkingRequest {

    Long id;

    Long adminId;

    Integer availableSlots;

    Boolean loyalty;

    String name;

    Integer totalSlots;

    LocationRequest location;

    OpeningHoursRequest openingHours;

    ParkingTypeRequest parkingType;

}
