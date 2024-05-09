package com.groupc.fourparks.infraestructure.model.request;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRateRequest {
    Long id;
    NewParkingRequest parkingId;
    VehicleTypeRequest vehicleTypeId;
    String rate;
}
