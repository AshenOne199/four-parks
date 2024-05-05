package com.groupc.fourparks.domain.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Long id;
    private String address;
    private Double latitude;
    private Double longitude;
    private City city;
}
