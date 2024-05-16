package com.groupc.fourparks.infraestructure.model.request;

import jakarta.validation.constraints.NotNull;
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


    @NotNull
    private String name;

    @NotNull
    private Integer carSlots;

    @NotNull
    private Integer bicycleSlots;

    @NotNull
    private Integer motorcycleSlots;

    @NotNull
    private Integer heavyVehicleSlots;

    @NotNull
    private Boolean loyalty;

    @NotNull
    private LocationRequest location;

    @NotNull
    private OpeningHoursRequest openingHours;

    @NotNull
    private ParkingTypeRequest parkingType;

    @NotNull
    private Long adminId;

}
