package com.groupc.fourparks.domain.port;

import java.util.List;
import java.util.Optional;

import com.groupc.fourparks.domain.model.Location;
import com.groupc.fourparks.domain.model.OpeningHours;
import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingType;
import com.groupc.fourparks.domain.model.User;

public interface ParkingPort {
    Parking save(Parking parking);
    Parking save(Parking parking, Location location,ParkingType parkingType,OpeningHours openingHours);
    Parking save(Parking parking, User user);
    Parking findParkingByName(String name);
    List<Parking> findParkings();
    Optional<Parking> findParkingByNameOptional(String name);
    void deleteParking(Parking parking);
    Parking findById(Long id);
}
