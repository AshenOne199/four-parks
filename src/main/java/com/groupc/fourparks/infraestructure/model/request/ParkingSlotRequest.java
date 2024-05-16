package com.groupc.fourparks.infraestructure.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlotRequest {
    Long id;

    @NotNull
    Long parkingId;

    @NotNull
    SlotStatusRequest slotStatusId;

    @NotNull
    VehicleTypeRequest vehicleTypeId;
}
