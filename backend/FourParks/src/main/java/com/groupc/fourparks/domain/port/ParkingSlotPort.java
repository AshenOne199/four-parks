package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingSlot;

public interface ParkingSlotPort {
    void save(ParkingSlot parkingSlot, Parking parking);
}
