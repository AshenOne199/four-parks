package com.groupc.fourparks.domain.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Parking {
    private Long id;
    private User admin;
    private String name;
    private Integer totalSlots;
    private Integer availableSlots;
    private Integer carSlots;
    private Integer bicycleSlots;
    private Integer motorcycleSlots;
    private Integer heavyVehicleSlots;
    private Boolean loyalty;
    private Location location;
    private ParkingType parkingType;
    private OpeningHours openingHours;
}
