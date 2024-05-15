package com.groupc.fourparks.domain.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleType {
    private Long id;
    private VehicleTypeEnum type;
}
