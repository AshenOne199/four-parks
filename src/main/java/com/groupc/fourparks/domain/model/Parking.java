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
    private Integer total_slots;
    private Integer available_slots;
    private Integer car_slots;
    private Integer bicycle_slots;
    private Integer motorcycle_slots;
    private Integer heavy_vehicle_slots;
    private Boolean loyalty;
    private Location location;
    private ParkingType parkingType;
    private OpeningHours openingHours;
}
