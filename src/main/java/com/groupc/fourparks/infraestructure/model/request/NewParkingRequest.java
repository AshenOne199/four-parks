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

    private Long id;

    private Long adminId;

    private String name;

    private Integer carSlots;

    private Integer bicycleSlots;

    private Integer motorcycleSlots;

    private Integer heavyVehicleSlots;

    private Boolean loyalty;

    private LocationRequest location;

    private OpeningHoursRequest openingHours;

    private ParkingTypeRequest parkingType;

}
