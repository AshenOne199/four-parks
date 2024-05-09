package com.groupc.fourparks.application.usecase;

import java.util.List;

import com.groupc.fourparks.infraestructure.model.dto.ParkingDto;
import com.groupc.fourparks.infraestructure.model.request.NewParkingRequest;
import com.groupc.fourparks.infraestructure.model.request.SetAdminToParkingRequest;

public interface ParkingService {
    ParkingDto newParking(NewParkingRequest newParkingRequest);
    ParkingDto getParking(String name);
    List<ParkingDto> getParkings();
    ParkingDto setAdmin(SetAdminToParkingRequest setAdminToParkingRequest);
    ParkingDto modifyParking(NewParkingRequest newParkingRequest);
    String deleteParking(String name);
}
