package com.groupc.fourparks.application.usecase;

import java.util.List;

import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;
import com.groupc.fourparks.infraestructure.model.request.SlotToShow;

public interface ParkingSlotService {
    SlotToShow newParkingSlot(ParkingSlotRequest newParkingRequest);
    SlotToShow getParkingSlot(Long id);
    List<SlotToShow> getParkingSlotsByParking(Long id);
    SlotToShow modifyParkingSlot(ParkingSlotRequest newParkingRequest);
    String deleteParkingSlot(Long id);
}
