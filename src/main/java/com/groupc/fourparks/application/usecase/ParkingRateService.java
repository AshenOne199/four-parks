package com.groupc.fourparks.application.usecase;

import java.util.List;

import com.groupc.fourparks.infraestructure.model.dto.ParkingRateDto;
import com.groupc.fourparks.infraestructure.model.request.ParkingRateRequest;

public interface ParkingRateService {
    ParkingRateDto newParkingRate(ParkingRateRequest parkingRateRequest);
    ParkingRateDto getParkingRate(Long id);
    List<ParkingRateDto> getParkingRatesByParking(Long id);
    ParkingRateDto modifyParkingRate(ParkingRateRequest parkingRateRequest);
    String deleteParkingRate(Long id);
}
