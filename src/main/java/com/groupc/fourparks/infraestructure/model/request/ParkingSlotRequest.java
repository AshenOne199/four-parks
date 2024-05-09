package com.groupc.fourparks.infraestructure.model.request;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlotRequest {
    Long id;
    NewParkingRequest parkingId;
    SlotStatusRequest slotStatusId;
    VehicleTypeRequest vehicleTypeId;
}
