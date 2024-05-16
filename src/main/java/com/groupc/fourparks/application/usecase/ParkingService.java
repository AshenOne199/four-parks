package com.groupc.fourparks.application.usecase;

import java.util.List;

import com.groupc.fourparks.infraestructure.model.dto.CityDto;
import com.groupc.fourparks.infraestructure.model.request.NewParkingRequest;
import com.groupc.fourparks.infraestructure.model.request.ParkingToShow;

public interface ParkingService {
    ParkingToShow newParking(NewParkingRequest newParkingRequest);
    ParkingToShow getParking(String name);
    List<ParkingToShow> getParkings();
    ParkingToShow modifyParking(NewParkingRequest newParkingRequest);
    String deleteParking(String name);
    List<CityDto> getCities();
}
