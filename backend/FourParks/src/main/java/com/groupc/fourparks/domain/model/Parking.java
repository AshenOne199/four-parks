package com.groupc.fourparks.domain.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Parking {
    private Long id;
    private String name;
    private int total_slots;
    private int available_slots;
    private boolean loyalty;
    private Location location;
    private ParkingType parkingType;
    private OpeningHours openingHours;
}
