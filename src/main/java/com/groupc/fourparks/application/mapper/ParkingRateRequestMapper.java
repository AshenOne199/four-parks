package com.groupc.fourparks.application.mapper;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.infraestructure.model.request.ParkingRateRequest;

@Mapper(componentModel = "spring")
public interface ParkingRateRequestMapper {
    ParkingRate toDomain(ParkingRateRequest parkingRateRequest);
}
