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

    String name;

    Integer car_slots;

    Integer bicycle_slots;

    Integer motorcycle_slots;

    Integer heavy_vehicle_slots;

    Boolean loyalty;

    LocationRequest location;

    OpeningHoursRequest openingHours;

    ParkingTypeRequest parkingType;

}
