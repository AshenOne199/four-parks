package com.groupc.fourparks.application.usecase;

import java.util.List;

import com.groupc.fourparks.infraestructure.model.dto.ParkingSlotDto;
import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;

public interface ParkingSlotService {
    void newParkingSlot(ParkingSlotRequest newParkingRequest);
    ParkingSlotDto getParkingSlot(Long id);
    List<ParkingSlotDto> getParkingSlotsByParking(Long id);
    ParkingSlotDto modifyParkingSlot(ParkingSlotRequest newParkingRequest);
    String deleteParkingSlot(Long id);
}
