package com.groupc.fourparks.domain.model;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingType {
    private Long id;
    private ParkingTypeEnum type;
}
