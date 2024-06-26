package com.groupc.fourparks.domain.port;

import java.util.List;
import java.util.Optional;

import com.groupc.fourparks.domain.model.Location;
import com.groupc.fourparks.domain.model.OpeningHours;
import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingType;

public interface ParkingPort {
    Parking save(Parking parking);
    Parking save(Parking parking, Location location, ParkingType parkingType, OpeningHours openingHours);
    Parking findParkingByName(String name);
    void findParkingByAdminId(Long adminId);
    List<Parking> findParkings();
    Optional<Parking> findParkingByNameOptional(String name);
    void deleteParking(Parking parking);
    Parking findById(Long id);
}
