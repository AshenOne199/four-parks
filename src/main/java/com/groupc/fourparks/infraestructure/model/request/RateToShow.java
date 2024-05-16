package com.groupc.fourparks.infraestructure.model.request;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateToShow {
    Long id;
    Float rate;
    Long parkingId;
    String vehicleTypeId;
}
