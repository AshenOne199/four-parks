package com.groupc.fourparks.application.usecase;

import java.util.List;

import com.groupc.fourparks.infraestructure.model.dto.CityDto;
import com.groupc.fourparks.infraestructure.model.dto.ParkingDto;
import com.groupc.fourparks.infraestructure.model.request.NewParkingRequest;

public interface ParkingService {
    ParkingDto newParking(NewParkingRequest newParkingRequest);
    ParkingDto getParking(String name);
    List<ParkingDto> getParkings();
    ParkingDto modifyParking(NewParkingRequest newParkingRequest);
    String deleteParking(String name);
    List<CityDto> getCities();
    ParkingDto getParkingById(Long id);
}
