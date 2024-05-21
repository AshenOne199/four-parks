package com.groupc.fourparks.domain.port;

import java.util.List;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.domain.model.VehicleType;

public interface ParkingRatePort {
    ParkingRate save(ParkingRate parkingRate);
    ParkingRate save(ParkingRate parkingRate, Parking parking, VehicleType vehicleType);
    ParkingRate getParkingRate(Long id);
    List<ParkingRate> getParkingRatesByParking(Parking parking);
    void deleteParkingRate(ParkingRate parkingRate);

}
