package com.groupc.fourparks.domain.port;

import java.util.List;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingSlot;
import com.groupc.fourparks.domain.model.SlotStatus;
import com.groupc.fourparks.domain.model.VehicleType;

public interface ParkingSlotPort {
    ParkingSlot save(ParkingSlot parkingSlot);
    ParkingSlot save(ParkingSlot parkingSlot, SlotStatus slotStatus,Parking parking, VehicleType vehicleType);
    ParkingSlot getParkingSlot(Long id);
    List<ParkingSlot> getParkingSlotsByParking(Parking parking);
    void deleteParkingSlot(ParkingSlot parkingSlot);
}
