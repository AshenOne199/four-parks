package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.domain.model.VehicleType;

public interface ParkingRatePort {
    ParkingRate save(ParkingRate parkingRate, Parking parking, VehicleType vehicleType);
}
