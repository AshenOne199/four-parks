package com.groupc.fourparks.infraestructure.model.request;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotToShow {
    Long id;
    Long parkingId;
    String slotStatusId;
    String vehicleTypeId;
}
