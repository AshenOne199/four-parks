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
    private Boolean loyalty;
    private Location location;
    private ParkingType parkingType;
    private OpeningHours openingHours;
}
