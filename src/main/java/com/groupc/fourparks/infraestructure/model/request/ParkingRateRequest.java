package com.groupc.fourparks.infraestructure.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRateRequest {
    Long id;

    @NotNull
    Long parkingId;

    @NotNull
    VehicleTypeRequest vehicleTypeId;

    @NotNull
    Double rate;
}
