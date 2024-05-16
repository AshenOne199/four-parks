package com.groupc.fourparks.application.usecase;

import java.util.List;

import com.groupc.fourparks.infraestructure.model.request.ParkingRateRequest;
import com.groupc.fourparks.infraestructure.model.request.RateToShow;

public interface ParkingRateService {
    RateToShow newParkingRate(ParkingRateRequest parkingRateRequest);
    RateToShow getParkingRate(Long id);
    List<RateToShow> getParkingRatesByParking(Long id);
    RateToShow modifyParkingRate(ParkingRateRequest parkingRateRequest);
    String deleteParkingRate(Long id);
}
